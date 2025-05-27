package sec04;

import app.Database;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import static com.mongodb.client.model.Filters.eq;

public class UpdateOneTest {
    public static void main(String[] args) {
        MongoCollection<Document> collection = Database.getCollection("users");

        // 실제 존재하는 _id 작성
        String id = "68354343fb6769625c69827e";

        // filters.eq() 같음 조건
        Bson query = eq("_id", new ObjectId(id));

        // Updates.combine() : 업데이트될 내용을 묶는 메서드
        // Updates.set("name", "modify name") : name 필드 값을 "modify name"으로 설정
        // Updates.currentTimeStamp("lastUpdated) : lastUpdated 필드 값으로 시간 설정
        // lastUpdated 필드 값으로 현재 시간(currentTimeStamp) 설정
        Bson updates = Updates.combine(
                Updates.set("name", "modify name"),
                Updates.currentTimestamp("lastUpdated"));

        UpdateResult result = collection.updateOne(query, updates);

        // 수정된 문서 개수 반환
        System.out.println("==> UpdateResult : " + result.getModifiedCount());

        Database.close();
    }
}