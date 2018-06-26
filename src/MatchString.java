/*
8. Leetcode 140 有点像
1. Given an array of strings `words` and a string `name`, find one substring of `name` that matches any word in `words`.
Put brackets around the matching substring in `name` and capitalize the first letter.

Sample input:
    words = ['B', 'Ar', 'O'].
    name = 'aaron'o on 1point3acres
Output:
    a[Ar]on
2. Followup. Find all possible ways of breaking bad..
    words = ['B', 'Ar', 'O']
    name = 'aaron'
Output:
    ['a[Ar][O]n', 'aaron', 'a[Ar]on', 'aar[O]n’]
 */


import java.util.ArrayList;
import java.util.List;

public class MatchString {

    public static void main(String[] args){
        String[] matchs= {"B","Ar","O"};
        String input = "aaron";
        MathStringSolution stringSolution = new MathStringSolution();
        List<String> ans = stringSolution.matchString(matchs,input);
        for(String a:ans){
            System.out.println(a);
        }
    }
}

class MathStringSolution{
    public List<String> matchString(String[] matches, String name){
        List<String> finalAns = new ArrayList<>();
        List<String> preAns = new ArrayList<>();
        preAns.add("");
        helper(matches,name,preAns,finalAns);
        return finalAns;
    }

    private void helper(String[] matches, String name,List<String> preAns, List<String> finalAns){
        if(name == null || name.length()==0 || name.isEmpty()) {
            finalAns.addAll(preAns);
            return;
        }


        for(String source: matches){
            if(name.startsWith(source.toLowerCase())){
                List<String> ans = new ArrayList<>();
                    for(String prefix: preAns){
                            ans.add(prefix+capitalizeSource(source));
                            helper(matches,name.substring(source.length()),ans,finalAns);
                        }
            }
        }

        List<String> ans = new ArrayList<>();
        for(String prefix: preAns){
            ans.add(prefix+String.valueOf(name.charAt(0)));
            helper(matches,name.substring(1),ans,finalAns);
        }
    }

    private String capitalizeSource(String input){
//        char[] output = input.toCharArray();
//        output[0] = Character.toUpperCase(output[0]);
//        return "["+output.toString()+"]";
        return "["+input+"]";
    }
}