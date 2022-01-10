package com.mine.integration.ql;

import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;
import com.ql.util.express.Operator;

/**
 * Copyright (c) 2018 Choice, Inc.
 * All Rights Reserved.
 * Choice Proprietary and Confidential.
 *
 * @author chisu
 * @since 2022/01/07
 */
public class QLExpressTest {

    public static void main(String[] args) throws Exception {

        ExpressRunner runner = new ExpressRunner();

        DefaultContext<String, Object> context = new DefaultContext<>();
        context.put("a", 1);
        context.put("b", 2);
        context.put("c", 3);
        String express = "a+b*c";
        Object execute = runner.execute(express, context, null, true, true);
        System.out.println(execute);

        runner.addFunctionOfClassMethod("取绝对值", Math.class.getName(), "abs", new Class[]{Double.class}, null);

        runner.addFunctionOfServiceMethod("打印", System.out, "println", new String[]{"String"}, null);

        // 注入serviceBean
        runner.addFunctionOfServiceMethod("", new Object(), "methodName", new Class[]{String.class, String.class}, null);



    }

    public static void operatorTest() throws Exception {
        ExpressRunner runner = new ExpressRunner();
        runner.addOperatorWithAlias("如果", "if",null);
        runner.addOperatorWithAlias("则", "then",null);
        runner.addOperatorWithAlias("否则", "else",null);

        String exp = "如果  (语文+数学+英语>270) 则 {return 1;} 否则 {return 0;}";
        DefaultContext<String, Object> context = new DefaultContext<String, Object>();
        runner.execute(exp,context,null,false,false,null);

    }

    public static void test() {

    }

    //定义一个继承自com.ql.util.express.Operator的操作符
    public class JoinOperator extends Operator {
        @Override
        public Object executeInner(Object[] list) throws Exception {
            Object opdata1 = list[0];
            Object opdata2 = list[1];
            if (opdata1 instanceof java.util.List) {
                ((java.util.List) opdata1).add(opdata2);
                return opdata1;
            } else {
                java.util.List result = new java.util.ArrayList();
                for (Object opdata : list) {
                    result.add(opdata);
                }
                return result;
            }
        }
    }

}
