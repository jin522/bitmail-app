package cn.dayutec.pigeon.access;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 过滤器
 **/
@Component
public class CORSFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse res = (HttpServletResponse) response;
        //允许所有域都具有访问资源的权限
        res.addHeader("Access-Control-Allow-Origin", "*");
        //允许跨域请求的方法GET,POST,OPTIONS，PUT,DELETE
        res.addHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS, PUT, DELETE");
        //允许跨域请求包含content-type，token,Content-Disposition
        res.addHeader("Access-Control-Allow-Headers", "Content-Type,token,Content-Disposition");

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        //暂不需要处理，无相关业务需求
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //暂不需要处理，无相关业务需求
    }
}


