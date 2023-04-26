import java.util.Arrays;

class Solution {
    public static int[] solution(int N, int[] stages) {
        int[] answer = new int[N];
        
        Arrays.sort(stages);
        
        while(answer[N -1] != 0) {
            for (int i = 0; i < stages.length; i++) {
                
            }
        }
        
        return answer;
    }

    public static void main(String[] args) {
        int[] stages = {2, 1, 2, 6, 2, 4, 3, 3};
        System.out.println(Arrays.toString(solution(5, stages)));
    }

}