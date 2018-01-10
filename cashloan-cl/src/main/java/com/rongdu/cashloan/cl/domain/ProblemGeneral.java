package com.rongdu.cashloan.cl.domain;

import java.io.Serializable;
import java.util.List;

public class ProblemGeneral implements Serializable{

    private int sort; //问题位置

    private String problem_name; //问题名称

    private List<ProblemGeneralDetail> problemGeneralDetails;

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getProblem_name() {
        return problem_name;
    }

    public void setProblem_name(String problem_name) {
        this.problem_name = problem_name;
    }

    public List<ProblemGeneralDetail> getProblemGeneralDetails() {
        return problemGeneralDetails;
    }

    public void setProblemGeneralDetails(List<ProblemGeneralDetail> problemGeneralDetails) {
        this.problemGeneralDetails = problemGeneralDetails;
    }

    @Override
    public String toString() {
        return "ProblemGeneral{" +
                "sort=" + sort +
                ", problem_name='" + problem_name + '\'' +
                ", problemGeneralDetails=" + problemGeneralDetails +
                '}';
    }
}
