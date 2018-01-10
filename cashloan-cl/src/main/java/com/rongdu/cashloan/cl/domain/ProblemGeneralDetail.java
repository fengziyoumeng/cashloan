package com.rongdu.cashloan.cl.domain;

import java.io.Serializable;

public class ProblemGeneralDetail implements Serializable{

    private int parent_sort; //问题位置，对应主表sort

    private int small_sort; //小排序

    private String problem_answer; //问题答案

    public int getParent_sort() {
        return parent_sort;
    }

    public void setParent_sort(int parent_sort) {
        this.parent_sort = parent_sort;
    }

    public String getProblem_answer() {
        return problem_answer;
    }

    public void setProblem_answer(String problem_answer) {
        this.problem_answer = problem_answer;
    }

    public int getSmall_sort() {
        return small_sort;
    }

    public void setSmall_sort(int small_sort) {
        this.small_sort = small_sort;
    }

    @Override
    public String toString() {
        return "ProblemGeneralDetail{" +
                "parent_sort=" + parent_sort +
                ", small_sort=" + small_sort +
                ", problem_answer='" + problem_answer + '\'' +
                '}';
    }
}
