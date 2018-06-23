import java.lang.reflect.Array;
import java.util.*;

public class SerializeDeserializeTree {

    public static void main(String[] args){
        TreeNode root = new TreeNode(1);
        TreeNode A1 = new TreeNode(2);
        TreeNode A2 = new TreeNode(7);
        TreeNode A3 = new TreeNode(3);
        root.addKid(A1);
        root.addKid(A2);
        root.addKid(A3);
        TreeNode B1 = new TreeNode(4);
        TreeNode B2 = new TreeNode(5);
        TreeNode B3 = new TreeNode(8);
        TreeNode B4 = new TreeNode(9);
        TreeNode B5 = new TreeNode(10);
        A1.addKid(B1);
        A1.addKid(B2);
        A3.addKid(B3);
        A3.addKid(B4);
        A3.addKid(B5);
        TreeNode C1 = new TreeNode(6);
        TreeNode C2 = new TreeNode(11);
        B2.addKid(C1);
        B4.addKid(C2);
        SerializeDeserializeTreeSolution solution = new SerializeDeserializeTreeSolution();
        System.out.println(solution.serialize(root));
        String input = "1,2,4,N,5,6,N,N,N,7,N,3,8,N,9,11,N,N,10,N,N,N";
        TreeNode node = solution.deserialize(input);
        System.out.println(solution.serialize(node));

    }
}

class TreeNode{
    int val;
    ArrayList<TreeNode> kids = new ArrayList<TreeNode>();

    public TreeNode(int val, ArrayList<TreeNode> nodes){
        this.val = val;
        this.kids = nodes;
    }

    public TreeNode(int val){
        this.val = val;
    }

    public void addKid(TreeNode node){
        this.kids.add(node);
    }

}

class SerializeDeserializeTreeSolution{

    public static final String seprator = ",";
    public static final String endMarker = "N";

    public String serialize(TreeNode node){
        StringBuilder sb = new StringBuilder();
        buildString(sb,node);
        return sb.toString();
    }

    private void buildString(StringBuilder sb, TreeNode node){
        if(node != null){
            sb.append(node.val);
            sb.append(seprator);
        }

        for(TreeNode kid: node.kids){
            buildString(sb,kid);
        }

        sb.append(endMarker);
        sb.append(seprator);
    }

    public TreeNode deserialize(String input){
        String[] words = input.split(seprator);
        Queue<String> queue = new LinkedList<String>();
        queue.addAll(Arrays.asList(words));
        TreeNode root = new TreeNode(Integer.valueOf(queue.poll()));
        Stack<TreeNode> parents = new Stack<>();
        parents.push(root);
        buildTree(queue,parents);
        return root;
    }

    private void buildTree(Queue<String> queue,Stack<TreeNode> parents){
        while(!parents.isEmpty() && !queue.isEmpty()){
            String val = queue.peek();
            if(val.equals(endMarker)){
                parents.pop();
                queue.poll();
            }else break;
        }

        while(!queue.isEmpty()){
            TreeNode parent = parents.peek();
            TreeNode kid = new TreeNode(Integer.valueOf(queue.poll()));
            parents.push(kid);
            parent.addKid(kid);
            buildTree(queue,parents);
        }

    }
}
