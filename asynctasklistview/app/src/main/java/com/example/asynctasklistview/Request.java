package com.example.asynctasklistview;
import java.util.Map;

public class Request {
    public final static int URL_TYPE_GET=0;
    public final static int URL_TYPE_POST_BODY=1;
    public final static int URL_TYPE_POST_FORM_DATA=2;
    private String url;
    private int method;
    private Object params;

    public Request(Builder builder){
        this.url=builder.url;
        this.method=builder.method;
        this.params=builder.params;
    }
    public Object getParams()
    {
        return params;
    }
    public int getMethod()
    {
        return method;
    }
    public String getUrl()
    {
        return url;
    }

    public static class Builder{

        protected String url;
        protected int method;
        protected Object params;

        Builder method(int method)
        {
            this.method=method;
            return this;
        }

        public Request build(){
            return new Request(this);
        }
    }

    public static class GetBuilder extends Builder{

        public GetBuilder(){
            this.method(URL_TYPE_GET);
        }

        public GetBuilder url(String url) {
            this.url=url;
            return this;
        }
        public GetBuilder params(Map map)
        {
            this.params=map;
            return this;
        }
    }
    public static class PostBodyBuilder extends Builder{

        public PostBodyBuilder(){
            this.method(URL_TYPE_POST_BODY);
        }

        public PostBodyBuilder url(String url) {
            this.url=url;
            return this;
        }
        public PostBodyBuilder params(String body)
        {
            this.params=body;
            return this;
        }
    }
    public static class PostFormDataBuilder extends Builder{

        public PostFormDataBuilder(){
            this.method(URL_TYPE_POST_FORM_DATA);
        }

        public PostFormDataBuilder url(String url) {
            this.url=url;
            return this;
        }
        public PostFormDataBuilder params(Map<String,String> map)
        {
            this.params=map;
            return this;
        }
    }

}
