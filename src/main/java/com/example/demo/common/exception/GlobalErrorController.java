package com.example.demo.common.exception;

import com.alibaba.fastjson.JSON;
import com.example.demo.common.response.DefaultResponse;
import com.example.demo.common.response.ReturnCodeEnum;
import com.example.demo.utils.Base64Utils;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 全局异常处理控制器，项目的所有错误都会转发到此控制器
 */
@Slf4j
@Controller
@RequestMapping("${server.error.path:${error.path:/error}}")
public class GlobalErrorController implements ErrorController {

    private static final String ERROR_PAGE = "error";

    @Autowired
    private SystemProperties systemProperties;

    @Autowired
    private ErrorAttributes errorAttributes;

    @Override
    public String getErrorPath() {
        return ERROR_PAGE;
    }

    /**
     * 返回网页
     * @param request
     * @param response
     * @return
     */
    /*
    @RequestMapping(produces = "text/html")
    public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
        ServletWebRequest servletWebRequest = new ServletWebRequest(request);
        Map<String, Object> errorAttributeMap = errorAttributes.getErrorAttributes(servletWebRequest, true);
        int status = (int) errorAttributeMap.get("status");
        response.setStatus(status);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(ERROR_PAGE);
        modelAndView.addAllObjects(errorAttributeMap);

        return modelAndView;
    }
    */


    /**
     * 返回 JSON
     * @param request request 请求
     * @param response response 响应
     * @return 自定义统一返回结构
     */
    @RequestMapping
    @ResponseBody
    public Object error(HttpServletRequest request, HttpServletResponse response) {
        ErrorAttributeInfo errorAttributeInfo = getErrorAttributes(request, systemProperties.getDebugMode());
        if (errorAttributeInfo.getThrowable() != null) {
            log.error("catch exception: ", errorAttributeInfo.getThrowable());
        }

        // 封装返回结构
        DefaultResponse defaultResponse;
        Throwable throwable = errorAttributeInfo.getThrowable();
        if (throwable instanceof BaseException) {
            // 自定义异常
            BaseException exception = (BaseException) errorAttributeInfo.getThrowable();
            defaultResponse = exception.getDefaultResponse();
        } else if (throwable instanceof ConstraintViolationException
                || throwable instanceof BindException
                || throwable instanceof MethodArgumentNotValidException) {
            // 参数校验异常
            defaultResponse = getParamErrorResponse(throwable);
        } else {
            // 其他异常
            switch (errorAttributeInfo.getStatus()) {
                case HttpServletResponse.SC_NOT_FOUND:
                    // 资源找不到
                    defaultResponse = DefaultResponse.fail(ReturnCodeEnum.HTTP_STATUS_CODE_404);
                    break;
                default:
                    defaultResponse = DefaultResponse.fail(ReturnCodeEnum.SERVER_ERROR);
                    break;
            }
        }

        // 若开启调试模式展示异常，并且主体空余，则返回异常堆栈信息
        if (systemProperties.getDebugMode()
                && errorAttributeInfo.getTrace() != null
                && defaultResponse.getContent() == null) {
            try {
                // base64 转码，避免查看明文信息时需要对字符串转义字符进行反转义处理
                defaultResponse.setContent(Base64Utils.encode(errorAttributeInfo.getTrace()));
            } catch (UnsupportedEncodingException e) {
                log.error("fail to base64 encode, can not return exception stack trace");
            }

        }

        return defaultResponse;
    }

    /**
     * 获取服务错误信息并进行封装
     * @param request HttpServletRequest
     * @param includeStackTrace 是否获取异常堆栈
     * @return
     */
    private ErrorAttributeInfo getErrorAttributes(HttpServletRequest request, boolean includeStackTrace) {
        ServletWebRequest servletWebRequest = new ServletWebRequest(request);
        Map<String, Object> errorAttributeMap = errorAttributes.getErrorAttributes(servletWebRequest, includeStackTrace);
        String jsonStr = JSON.toJSONString(errorAttributeMap);
        ErrorAttributeInfo errorAttributeInfo = JSON.parseObject(jsonStr, ErrorAttributeInfo.class);
        errorAttributeInfo.setThrowable(errorAttributes.getError(servletWebRequest));

        return errorAttributeInfo;
    }

    /**
     * 获取参数校验异常的返回结构
     * 当捕获{@link ConstraintViolationException}异常时，返回错误信息为{@link Set}无序
     * 故这种情况下错误信息不一定是按判断逻辑排序的
     *
     * @see ConstraintViolationException 离散参数（控制器方法里的参数）校验失败
     * @see BindException Bean中的参数校验失败(非JSON形式，即不使用@RequestBody)
     * @see MethodArgumentNotValidException Bean中的参数校验失败(JSON形式，使用@RequestBody)
     * @param throwable 参数校验异常
     * @return
     */
    private DefaultResponse getParamErrorResponse(Throwable throwable) {
        List<ParamError> paramErrorList = new ArrayList<>();
        if (throwable instanceof ConstraintViolationException) {
            Set<ConstraintViolation<?>> violationSet = ((ConstraintViolationException) throwable).getConstraintViolations();
            Object[] violationArray = violationSet.toArray();
            for (int i = 0; i < violationArray.length; i++) {
                ConstraintViolation<?> violation = (ConstraintViolation<?>) violationArray[i];
                PathImpl pathImpl = (PathImpl) violation.getPropertyPath();
                String paramName = pathImpl.getLeafNode().getName();
                paramErrorList.add(new ParamError(paramName, violation.getMessageTemplate()));
            }
        } else if (throwable instanceof BindException || throwable instanceof MethodArgumentNotValidException) {
            BindingResult bindingResult = null;
            if (throwable instanceof BindException) {
                bindingResult = ((BindException) throwable).getBindingResult();
            } else if (throwable instanceof MethodArgumentNotValidException) {
                bindingResult = ((MethodArgumentNotValidException) throwable).getBindingResult();
            }
            List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
            for (int i = 0; i < fieldErrorList.size(); i++) {
                FieldError fieldError = fieldErrorList.get(i);
                paramErrorList.add(new ParamError(fieldError.getField(), fieldError.getDefaultMessage()));
            }
        }

        return DefaultResponse.fail(ReturnCodeEnum.PARAM_ERROR, paramErrorList);
    }
}