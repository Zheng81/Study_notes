# Servlet中的疑问

> **在很多的地方中都说会在在客户端在发送请求给Web浏览器后，Servlet中会调用service方法，但在实际中的Servlet代码块中却没有涉及到service方法，这是为什么?**

其实Servlet是一个接口，他要靠子类去实现，而sun公司就提供了两个默认的接口实现类(GenericServlet和HttpServlet),但其中的GenericServlet是一个抽象类，它没有去实现Http请求处理(估计是给需要的人进行设计自己想要的处理方法吧)，而HttpServlet中就有实现了Http请求处理(即service)，HttpServlet中对它进行了实现，将ServletRequest和ServletResponse转变成为HttpServletRequest和HttpServletResponse具体实现如下:

```java
@Override
    public void service(ServletRequest req, ServletResponse res)
        throws ServletException, IOException {

        HttpServletRequest  request;
        HttpServletResponse response;

        try {
            request = (HttpServletRequest) req;
            response = (HttpServletResponse) res;
        } catch (ClassCastException e) {
            throw new ServletException("non-HTTP request or response");
        }
        service(request, response);
    }
}

   protected void service(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {

        String method = req.getMethod();

        if (method.equals(METHOD_GET)) {
            long lastModified = getLastModified(req);
            if (lastModified == -1) {
                // servlet doesn't support if-modified-since, no reason
                // to go through further expensive logic
                doGet(req, resp);
            } else {
                long ifModifiedSince;
                try {
                    ifModifiedSince = req.getDateHeader(HEADER_IFMODSINCE);
                } catch (IllegalArgumentException iae) {
                    // Invalid date header - proceed as if none was set
                    ifModifiedSince = -1;
                }
                if (ifModifiedSince < (lastModified / 1000 * 1000)) {
                    // If the servlet mod time is later, call doGet()
                    // Round down to the nearest second for a proper compare
                    // A ifModifiedSince of -1 will always be less
                    maybeSetLastModified(resp, lastModified);
                    doGet(req, resp);
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
                }
            }

        } else if (method.equals(METHOD_HEAD)) {
            long lastModified = getLastModified(req);
            maybeSetLastModified(resp, lastModified);
            doHead(req, resp);

        } else if (method.equals(METHOD_POST)) {
            doPost(req, resp);

        } else if (method.equals(METHOD_PUT)) {
            doPut(req, resp);

        } else if (method.equals(METHOD_DELETE)) {
            doDelete(req, resp);

        } else if (method.equals(METHOD_OPTIONS)) {
            doOptions(req,resp);

        } else if (method.equals(METHOD_TRACE)) {
            doTrace(req,resp);

        } else {
            //
            // Note that this means NO servlet supports whatever
            // method was requested, anywhere on this server.
            //

            String errMsg = lStrings.getString("http.method_not_implemented");
            Object[] errArgs = new Object[1];
            errArgs[0] = method;
            errMsg = MessageFormat.format(errMsg, errArgs);

            resp.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED, errMsg);
        }
    }

HttpServlet
```

而对于service方法，一般来说这个方法是不需要重写的，因为在HttpServlet中已经有了很好的实现，它会根据请求的方法名（GET，POST），调用doGet，doPos以及其他的doXXX方法，也就是说service是用来转向的，所以我们一般写一个servlet，只需要重写doGet或者doPost就可以了。如果重写了service方法，那么servlet容器就会把请求交给这个方法来处理，倘若你重写的service方法没有调用doXXX，即使你在Servlet中又重写了其他doGet doPost等也是不会被调用的 因为Servlet的service被自动调用（就像init destory一样）。

故在代码中只需要去编写doGet或者是doPost方法即可。

