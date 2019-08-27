package com.example.mycourse.bean;

public class ExerciseBean {
    public int id;//每章习题id
    public String title;//每章习题标题
    public String content;//每章习题的数目
    public int background;//每章习题前面的序号背景
    public int subjectId;//每章习题的id
    public String subject;//每道习题的题干
    public String a;//每道习题的A选项
    public String b;//每道习题的B选项
    public String c;//每道习题的C选项
    public String d;//每道习题的D选项
    public int answer;//每道习题的正确答案
    /**
     *select为0表示所选项是正确的，1表示选中的A选项是错误的，2表示选中的B选项是错误的，3表示选中的C选项是错误的，4表示选中的D选项是错误的
     * */
    public int select;
}
