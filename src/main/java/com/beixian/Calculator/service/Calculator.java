package com.beixian.Calculator.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Stack;

public class Calculator {

    public static final char SUBTRACT = '-';
    public static final char MULTIPLY = '*';
    public static final char DIVISION = '/';
    public static final char ADD = '+';
    //用一个栈来存储运算结果历史
    private final Stack<BigDecimal> history;
    //用一个栈来存储undo历史
    private final Stack<BigDecimal> undoHistory;
    //当前运算结果
    private BigDecimal currentResult;
    //小数精确度 默认：精确到小数后两位
    private Integer scale = 2;
    //舍入模式 默认：RoundingMode.DOWN 粗暴截断舍弃位，不考虑任何进位舍位操作
    private RoundingMode roundingMode = RoundingMode.DOWN;

    public Calculator() {
        history = new Stack<>();
        undoHistory = new Stack<>();
        currentResult = BigDecimal.ZERO;
    }

    public void setScale(Integer scale) {
        this.scale = scale;
    }

    public Integer getScale() {
        return scale;
    }

    public RoundingMode getRoundingMode() {
        return roundingMode;
    }

    public void setRoundingMode(RoundingMode roundingMode) {
        this.roundingMode = roundingMode;
    }

    /**
     * 把当前运算结果保存到栈，并清空undo的历史
     * 添加新计算了就清空undo历史
     */
    private void recordHistory() {
        history.push(currentResult);
        undoHistory.clear();
    }

    /**
     * 加法操作，执行前把当前运算结果保存到栈
     *
     * @param num
     */
    public void add(BigDecimal num) {
        recordHistory();
        currentResult = currentResult.add(num);
    }

    /**
     * 减法操作，执行前把当前运算结果保存到栈
     *
     * @param num
     */
    public void subtract(BigDecimal num) {
        recordHistory();
        currentResult = currentResult.subtract(num);
    }

    /**
     * 乘法操作，执行前把当前运算结果保存到栈
     *
     * @param num
     */
    public void multiply(BigDecimal num) {
        recordHistory();
        currentResult = currentResult.multiply(num);
    }

    /**
     * 除法操作，执行前把当前运算结果保存到栈
     * 并且做被除数是否零判断
     *
     * @param num
     */
    public void divide(BigDecimal num) {
        if (num.compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalArgumentException("Warning:Division by zero is not allowed.");
        }
        recordHistory();
        currentResult = currentResult.divide(num, scale, roundingMode);
    }

    /**
     * undo 回撤上一个运算结果
     */
    public void undo() {
        if (!history.isEmpty()) {
            undoHistory.push(currentResult);
            currentResult = history.pop();
        } else {
            System.out.println("Warning:There is no undo operation");
        }
    }

    /**
     * undo 回撤上一个undo结果
     */
    public void redo() {
        if (!undoHistory.isEmpty()) {
            history.push(currentResult);
            currentResult = undoHistory.pop();
        } else {
            System.out.println("Warning:There is no undo operation to redo");
        }
    }

    /**
     * 获取当前运算结果
     *
     * @return
     */
    public BigDecimal getResult() {
        currentResult = currentResult.setScale(scale, roundingMode);
        return currentResult;
    }

}
