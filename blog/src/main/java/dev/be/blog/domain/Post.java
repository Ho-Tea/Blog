package dev.be.blog.domain;

public class Post {
    private Category category;
    private Context context;
    private Profile user;

    private Post(){}

    private Post(Category category , Context context, Profile user){
        this.category = category;
        this.context = context;
        this.user = user;
    }

    public static Post register(Category category, Context context, Profile user){
        return new Post(category, context, user);
    }

    public void edit(String title, String text){
        context.edit(title, text);
    }
    public void editTitle(String title){
        context.editTitle(title);
    }

    public void editText(String text){
        context.editText(text);
    }

    public void print(){
        category.printCategory();
        context.print();
        user.printNickname();
    }


}
