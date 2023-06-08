package dev.be.blog.domain;

public class User {
    private String name;
    private String nickname;
    private int age;
    private String email;
    private String address;
    private String image;   // 프로필 이미지는 우선 String으로 표현


    private User(Builder builder) {
        this.name = builder.name;
        this.nickname = builder.nickname;
        this.age = builder.age;
        this.email = builder.email;
        this.address = builder.address;
        this.image = builder.image;
    }

    public String getNickname() {
        return nickname;
    }

    // 빌더 호출, 외부에서 Profile.builder() 으로 접근할 수 있도록 static 메소드로 생성
    public static Builder builder() {
        return new Builder();
    }

    // static 형태의 inner class 생성
    public static class Builder {
        private String name;
        private String nickname;
        private int age;
        private String email;
        private String address;
        private String image;   // 프로필 이미지는 우선 String으로 표현

        private Builder() {};

        public Builder name(String name) {
            this.name = name;
            return this;
        }
        public Builder nickname(String nickname) {
            this.nickname = nickname;
            return this;
        }

        public Builder age(int age) {
            this.age = age;
            return this;
        }
        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder address(String address) {
            this.address = address;
            return this;
        }

        public Builder image(String image) {
            this.image = image;
            return this;
        }

        // 마지막에 build 메소드를 실행하면 this가 return 되도록 구현
        public User build() {
            return new User(this);
        }
    }


    @Override
    public String toString() {
        return "Profile{" +
                "name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}