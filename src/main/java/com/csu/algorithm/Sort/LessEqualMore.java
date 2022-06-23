import java.util.Arrays;

public class LessEqualMore {

    public static void lessEqualMore(int[] arr , int num){
        // 两个域，小，大
        int less = -1;
        int more = arr.length;
        int i =0;
        while (i < more){
            if(arr[i]>num){
                swap(arr,--more , i);
            }
            else if(arr[i]<=num){
                swap(arr,++less , i++);
            }
        }
    }

    public static void swap(int[] arr,int i,int j){
        if(arr[i] == arr[j]) return;
        arr[i]^=arr[j];
        arr[j]^=arr[i];
        arr[i]^=arr[j];
    }

    public static void main(String[] args) {
        int[] a = new int[]{5,1,3,4,2,0,7,-1,-10,3,3};
        lessEqualMore(a,3);
        System.out.println(Arrays.toString(a));
    }

}
