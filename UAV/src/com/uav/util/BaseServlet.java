package com.uav.util;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // ��ȡ�����ʶ
            String methodName = request.getParameter("method");
            // ��ȡָ������ֽ������
            Class<? extends BaseServlet> clazz = this.getClass();//�����thisָ���Ǽ̳�BaseServlet����
            // ͨ������ֽ�������ȡ�������ֽ������
            Method method = clazz.getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            // �÷���ִ��
            method.invoke(this, request, response);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}

