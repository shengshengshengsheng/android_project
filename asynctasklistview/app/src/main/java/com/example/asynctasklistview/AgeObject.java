package com.example.asynctasklistview;

import java.util.List;

public class AgeObject extends BaseResponseObject{

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * pageNo : 1
         * totalRecord : 100
         * userList : [{"name":"Kevin Lee","age":57},{"name":"George Martin","age":62},{"name":"Betty Garcia","age":73},{"name":"Ruth Hall","age":29},{"name":"Joseph White","age":68},{"name":"Shirley Brown","age":23},{"name":"Kenneth Wilson","age":84},{"name":"Anthony Walker","age":27},{"name":"Eric Lopez","age":93},{"name":"Melissa Miller","age":33},{"name":"Sarah Miller","age":20},{"name":"Cynthia Wilson","age":75},{"name":"Mary Lee","age":30},{"name":"Ruth Robinson","age":46},{"name":"Mary Smith","age":23}]
         */
        private int pageNo;
        private int totalRecord;
        private List<DataBean.UserListBean> userList;

        public int getPageNo() {
            return pageNo;
        }

        public void setPageNo(int pageNo) {
            this.pageNo = pageNo;
        }

        public int getTotalRecord() {
            return totalRecord;
        }

        public void setTotalRecord(int totalRecord) {
            this.totalRecord = totalRecord;
        }

        public List<DataBean.UserListBean> getUserList() {
            return userList;
        }

        public void setUserList(List<DataBean.UserListBean> userList) {
            this.userList = userList;
        }

        public static class UserListBean {
            /**
             * name : Kevin Lee
             * age : 57
             */

            private String name;
            private int age;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getAge() {
                return age;
            }

            public void setAge(int age) {
                this.age = age;
            }
        }
    }
}
