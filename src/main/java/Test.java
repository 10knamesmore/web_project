import com.wanger.exceptions.DocumentExistException;
import com.wanger.mongoDB.MatchDataOperation;
import com.wanger.mongoDB.TeamDataOperation;
import com.wanger.statics.Statics;


public class Test {
    public static void main(String[] args) {

        try {
            MatchDataOperation.save(Statics.TEST_MATCH_DATA);
        } catch (DocumentExistException e) {
            System.out.println("document 存在");
        }
        //        System.out.println("Save result: " + save1 + ", " + save2 + ", " + save3);
    }
}
