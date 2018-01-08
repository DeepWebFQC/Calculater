package Calculater;


import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import sun.nio.cs.ext.MacHebrew;

import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
import java.util.Stack;


public class CalculaterController{
    /**
     * 保存输入的表达式
     */
    private ArrayList<String> expression = new ArrayList<>();

    /**
     * 角运算模式
     */
    private boolean Degree = true;
    @FXML
    private Label modeText;

    /**
     * 计算时计数
     */
    private int i = 0;

    private double result;



    /**
     * 运算符优先级表
     */
    private HashMap<String, Integer> priorTable = new HashMap<>();
    {
        result=0;
        priorTable.put("#", 0);
        priorTable.put("ANS", 0);
        priorTable.put("(", 1);
        priorTable.put(")", 1);
        priorTable.put("tan(", 1);
        priorTable.put("sin(", 1);
        priorTable.put("cos(", 1);
        priorTable.put("lg(", 1);
        priorTable.put("ln(", 1);
        priorTable.put("sqrt(", 1);
        priorTable.put("+", 2);
        priorTable.put("-", 2);
        priorTable.put("*", 3);
        priorTable.put("/", 3);
        priorTable.put("^", 4);
        priorTable.put("%", 4);
        priorTable.put("!", 5);

    }

    @FXML
    private TextField textUp;

    @FXML
    private TextField textDown;

    private boolean calculate() throws WrongExpressionException{
        /**
         * 运算数栈
         */
        Stack<Double> numberStack = new Stack<>();

        /**
         * 运算符栈
         */
        Stack<String> operatorStack = new Stack<>();
        try{
            i=0;
            numberStack.clear();
            operatorStack.clear();
            operatorStack.push("#");
            expression.add("#");
            String s, operator;
            Double temp;
            while (!operatorStack.isEmpty()){
                s = next();
                if (!isOperator(s)){
                    if (s.equals("e")){
                        numberStack.push(Math.E);
                        continue;
                    }
                    try {
                        numberStack.push(Double.parseDouble(s));
                    }catch (NumberFormatException e){
                        throw new WrongExpressionException("数字格式错误：" + s);
                    }
                    continue;
                }
                if (priorTable.get(operatorStack.peek()) < priorTable.get(s)){
                    if (
                            (s.equals("tan(")
                            || s.equals("sin(")
                            || s.equals("cos(")
                            || s.equals("lg(")
                            || s.equals("ln(")
                            || s.equals("(")
                            || s.equals("sqrt("))
                            && !numberStack.isEmpty()
                            && operatorStack.peek().equals("#")){
                        throw new WrongExpressionException(s + ": 之前需要运算符");
                    }
                    //如果下一个元素优先级大于栈顶元素优先级，则下一个元素入栈
                    operatorStack.push(s);
                }else {
                    //这里下一个运算符的优先级低于栈顶运算符的优先级
                    if (s.equals("tan(")){
                        operatorStack.push(s);
                        continue;
                    }else if (s.equals("sin(")){
                        operatorStack.push(s);
                        continue;
                    }else if (s.equals("cos(")){
                        operatorStack.push(s);
                        continue;
                    }else if (s.equals("lg(")){
                        operatorStack.push(s);
                        continue;
                    }else if (s.equals("ln(")){
                        operatorStack.push(s);
                        continue;
                    }else if (s.equals("sqrt(")){
                        operatorStack.push(s);
                        continue;
                    }else if (s.equals("(")){
                        operatorStack.push(s);
                        continue;
                    }else if (s.equals("ANS")){
                        numberStack.push(result);
                        continue;
                    }
                    while (priorTable.get(operatorStack.peek()) >= priorTable.get(s)){
                        operator = operatorStack.pop();
                        if (operator.equals("tan(")){
                            if (!s.equals(")")){
                                throw new WrongExpressionException("括号不匹配");
                            }
                            if (numberStack.isEmpty()){
                                throw new WrongExpressionException(operator + ": 没有操作数");
                            }
                            temp = numberStack.pop();
                            if (Degree){
                                temp = Math.toRadians(temp);
                            }
                            numberStack.push(Math.tan(temp));
                            break;
                        }else if (operator.equals("sin(")){
                            if (!s.equals(")")){
                                throw new WrongExpressionException("括号不匹配");
                            }
                            if (numberStack.isEmpty()){
                                throw new WrongExpressionException(operator + ": 没有操作数");
                            }
                            temp = numberStack.pop();
                            if (Degree){
                                temp = Math.toRadians(temp);
                            }
                            numberStack.push(Math.sin(temp));
                            break;
                        }else if (operator.equals("cos(")){
                            if (!s.equals(")")){
                                throw new WrongExpressionException("括号不匹配");
                            }
                            if (numberStack.isEmpty()){
                                throw new WrongExpressionException(operator + ": 没有操作数");
                            }
                            temp = numberStack.pop();
                            if (Degree){
                                temp = Math.toRadians(temp);
                            }
                            numberStack.push(Math.cos(temp));
                            break;
                        }else if (operator.equals("lg(")){
                            if (!s.equals(")")){
                                throw new WrongExpressionException("括号不匹配");
                            }
                            if (numberStack.isEmpty()){
                                throw new WrongExpressionException(operator + ": 没有操作数");
                            }
                            numberStack.push(Math.log10(numberStack.pop()));
                            break;
                        }else if (operator.equals("ln(")){
                            if (!s.equals(")")){
                                throw new WrongExpressionException("括号不匹配");
                            }
                            if (numberStack.isEmpty()){
                                throw new WrongExpressionException(operator + ": 没有操作数");
                            }
                            numberStack.push(Math.log(numberStack.pop()));
                            break;
                        }else if (operator.equals("sqrt(")){
                            if (!s.equals(")")){
                                throw new WrongExpressionException("括号不匹配");
                            }
                            if (numberStack.isEmpty()){
                                throw new WrongExpressionException(operator + ": 没有操作数");
                            }
                            numberStack.push(Math.sqrt(numberStack.pop()));
                            break;
                        }else if (operator.equals("(")){
                            if (!s.equals(")")){
                                throw new WrongExpressionException("括号不匹配");
                            }
                            if (numberStack.isEmpty()){
                                throw new WrongExpressionException(operator + ": 没有操作数");
                            }
                            break;
                        }else if (operator.equals("+")){
                            temp = numberStack.pop();
                            numberStack.push(numberStack.pop() + temp);
                        }else if (operator.equals("-")){
                            temp = numberStack.pop();
                            numberStack.push(numberStack.pop() - temp);
                        }else if (operator.equals("*")){
                            temp = numberStack.pop();
                            numberStack.push(numberStack.pop() * temp);
                        }else if (operator.equals("/")){
                            temp = numberStack.pop();
                            numberStack.push(numberStack.pop() / temp);
                        }else if (operator.equals("^")){
                            temp = numberStack.pop();
                            numberStack.push(Math.pow(numberStack.pop(), temp));
                        }else if (operator.equals("%")){
                            temp = numberStack.pop();
                            numberStack.push(numberStack.pop() % temp);
                        }else if (operator.equals("!")){
                            numberStack.push(jiecheng(numberStack.pop()));
                        }
                        else if (operator.equals("#")){
                            result = numberStack.pop();
                            if (numberStack.isEmpty()){
                                return true;
                            }else{
                                throw new WrongExpressionException("Number Overflow");
                            }
                        }else {
                            throw new WrongExpressionException("Unknow Error");
                        }
                    }
                    if (!s.equals(")")){
                        operatorStack.push(s);
                    }

                }
            }
            result = numberStack.pop();
            if (numberStack.isEmpty()){
                return true;
            }else{
                throw new WrongExpressionException("Number Overflow");
            }
        }catch (WrongExpressionException e){
            throw new WrongExpressionException(e.getMessage());
        }catch (Exception e){
            throw new WrongExpressionException("表达式错误");
        }
    }

    private int findEnd() throws WrongExpressionException{
        int s = 1;
        int j = i;
        while (s > 0){
            if (j == expression.size()){
                throw new WrongExpressionException("括号不匹配");
            }
            if (expression.get(j).equals("tan(") ||
                expression.get(j).equals("sin(") ||
                expression.get(j).equals("cos(") ||
                expression.get(j).equals("ln(") ||
                expression.get(j).equals("lg(") ||
                expression.get(j).equals("(") ||
                expression.get(j).equals("sqrt(")){
                s++;
            }else if (expression.get(j).equals(")")){
                s--;
            }
            j++;
        }
        return s;
    }

    private double jiecheng(double n){
        double r = 1;
        for (double i = 1; i <= n; i++){
            r *= i;
        }
        return r;
    }

    private boolean isOperator(String str){
        return priorTable.get(str) != null;
    }

    private String next(){
        String next = expression.get(i++);
        if (priorTable.get(next) != null){
            return next;
        }else if (next.equals("PI")){
            return Double.toString(Math.PI);
        }
        while (priorTable.get(expression.get(i)) == null){
            //如果是一串数字
            next += expression.get(i++);
        }
        return next;
    }

    @FXML
    private void mouseEnter(MouseEvent event){

        try {
            Pane node = (Pane) event.getTarget();
            Rectangle rectangle = (Rectangle)node.getChildren().get(0);
            rectangle.setFill(Color.rgb(171,171,171));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void mouseLeave(MouseEvent event){
        try {
            Pane node = (Pane) event.getTarget();
            Rectangle rectangle = (Rectangle)node.getChildren().get(0);
            rectangle.setFill(Color.rgb(211,211,211));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void mouseLeaveNumber(MouseEvent event){
        try {
            Pane node = (Pane) event.getTarget();
            Rectangle rectangle = (Rectangle)node.getChildren().get(0);
            rectangle.setFill(Color.rgb(195,197,217));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void dianClick(MouseEvent event){
        expression.add(".");
        showExpressionDown();
    }

    @FXML
    private void zeroClick(MouseEvent event){
        expression.add("0");
        showExpressionDown();
    }

    @FXML
    private void oneClick(MouseEvent event){
        expression.add("1");
        showExpressionDown();
    }

    @FXML
    private void twoClick(MouseEvent event){
        expression.add("2");
        showExpressionDown();
    }

    @FXML
    private void threeClick(MouseEvent event){
        expression.add("3");
        showExpressionDown();
    }

    @FXML
    private void fourClick(MouseEvent event){
        expression.add("4");
        showExpressionDown();
    }

    @FXML
    private void fiveClick(MouseEvent event){
        expression.add("5");
        showExpressionDown();
    }

    @FXML
    private void sixClick(MouseEvent event){
        expression.add("6");
        showExpressionDown();
    }

    @FXML
    private void sevenClick(MouseEvent event){
        expression.add("7");
        showExpressionDown();
    }

    @FXML
    private void eightClick(MouseEvent event){
        expression.add("8");
        showExpressionDown();
    }

    @FXML
    private void nineClick(MouseEvent event){
        expression.add("9");
        showExpressionDown();
    }

    @FXML
    private void quyuClick(MouseEvent event){
        expression.add("%");
        showExpressionDown();
    }

    @FXML
    private void genhaoClick(MouseEvent event){
        expression.add("sqrt(");
        showExpressionDown();
    }

    @FXML
    private void zuokuohaoClick(MouseEvent event){
        expression.add("(");
        showExpressionDown();
    }

    @FXML
    private void youkuohaoClick(MouseEvent event){
        expression.add(")");
        showExpressionDown();
    }

    @FXML
    private void chengfangClick(MouseEvent event){
        expression.add("^");
        showExpressionDown();
    }

    @FXML
    private void jiaClick(MouseEvent event){
        expression.add("+");
        showExpressionDown();
    }

    @FXML
    private void jianClick(MouseEvent event){
        expression.add("-");
        showExpressionDown();
    }

    @FXML
    private void chengClick(MouseEvent event){
        expression.add("*");
        showExpressionDown();
    }

    @FXML
    private void chuClick(MouseEvent event){
        expression.add("/");
        showExpressionDown();
    }

    @FXML
    private void tanClick(MouseEvent event){
        expression.add("tan(");
        showExpressionDown();
    }

    @FXML
    private void sinClick(MouseEvent event){
        expression.add("sin(");
        showExpressionDown();
    }

    @FXML
    private void cosClick(MouseEvent event){
        expression.add("cos(");
        showExpressionDown();
    }

    @FXML
    private void PIClick(MouseEvent event){
        expression.add("PI");
        showExpressionDown();
    }

    @FXML
    private void lgClick(MouseEvent event){
        expression.add("lg(");
        showExpressionDown();
    }

    @FXML
    private void lnClick(MouseEvent event){
        expression.add("ln(");
        showExpressionDown();
    }

    @FXML
    private void jiechengClick(MouseEvent event){
        expression.add("!");
        showExpressionDown();
    }

    @FXML
    private void pingfangClick(MouseEvent event){
        expression.add("^");
        expression.add("2");
        showExpressionDown();
    }

    @FXML
    private void daoshuClick(MouseEvent event){
        String temp = expression.get(expression.size() - 1);
        expression.remove(expression.size() - 1);
        expression.add("1");
        expression.add("/");
        expression.add(temp);
        showExpressionDown();
    }

    @FXML
    private void eClick(MouseEvent event){
        expression.add("e");
        showExpressionDown();
    }

    @FXML
    private void qiehuanClick(MouseEvent event){
        if (Degree){
            modeText.setText("弧度");
            Degree = false;
        }else {
            modeText.setText("角度");
            Degree = true;
        }
    }

    @FXML
    private void ANSClick(MouseEvent event){
        expression.add("ANS");
        showExpressionDown();
    }

    @FXML
    private void dengyuClick(MouseEvent event){
        clearDown();
        showExpressionUp();
        textUp.appendText(" =");
        try {
            calculate();
            showResultDown();
        }catch (WrongExpressionException e){
            showErrorDown(e);
        }

        expression.clear();
    }

    @FXML
    private void CClick(MouseEvent event){
        expression.clear();
        showExpressionDown();
    }

    @FXML
    private void tuigeClick(MouseEvent event){
        if (expression.isEmpty()){
            showExpressionDown();
            return;
        }
        expression.remove(expression.size() - 1);
        showExpressionDown();
    }

    private void showExpressionDown(){
        String str = "";
        for (String s : expression){
            str += s;
        }
        textDown.setText(str);
    }

    private void showExpressionUp(){
        String str = "";
        for (String s : expression){
            str += s;
        }
        textUp.setText(str);
    }

    private void showResultDown(){
        textDown.setText((Double.toString(result)));
    }

    private void showErrorDown(Exception e){
        textDown.setText(e.getMessage());
    }

    private void clearDown(){
        textDown.clear();
    }
}

class WrongExpressionException extends Exception {  // 自定义的类
    WrongExpressionException(String s) {
        super(s);
    }
}