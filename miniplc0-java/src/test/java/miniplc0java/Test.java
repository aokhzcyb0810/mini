package miniplc0java;

public class Test {
    public static void main(String[] args){
        String str="23112";
        int len=str.length();
        long num=0,i=1;
        while(len>0){
            len--;
            num+=((long)(str.charAt(len))-48)*i;
            i*=10;
        }
        System.out.println(num);

    }
}
