package cn.hwd.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@WebFilter("/*")
public class SensitiveWordsFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        ServletRequest proxy_req = (ServletRequest) Proxy.newProxyInstance(req.getClass().getClassLoader(), req.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                if (method.getName().equals("getParameter")) {
                    String value = (String) method.invoke(req, args);
                    if (value != null) {
                        for (String str : list) {
                            if (value.contains(str)) {
                                value = value.replaceAll(str, "***");
                            }
                        }
                    }
                    return value;
                }
                if (method.getName().equals("getParameterMap")) {
                    Map<String, String[]> map = (Map<String, String[]>) method.invoke(req, args);


                    Set<String> key = map.keySet();
                    for (String str : key) {
                        for (String s : list) {
                            if (map.get(str)[0].contains(s)) {
                                map.get(str)[0] = map.get(str)[0].replaceAll(s, "***");
                            }
                        }
                    }
                    return map;
                }


                return method.invoke(req, args);

            }
        });
        chain.doFilter(proxy_req, resp);
    }

    public List<String> list = new ArrayList<String>();

    public void init(FilterConfig config) throws ServletException {

        //加载配置文件，加载一次

        try {
            ServletContext servletContext = config.getServletContext();
            String realPath = servletContext.getRealPath("/WEB-INF/classes/敏感词汇.txt");
            BufferedReader br = new BufferedReader(new FileReader(realPath));

            String line = null;
            while ((line = br.readLine()) != null) {
                list.add(line);
            }

            br.close();

        } catch (Exception e) {

        }

    }

}
