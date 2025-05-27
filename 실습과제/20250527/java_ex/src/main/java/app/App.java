package app;

import app.domain.Todo;
import com.mongodb.client.MongoCollection;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static com.mongodb.client.model.Filters.eq;
public class App {
    public static void main(String[] args) {
        // todo 컬렉션 추출
        MongoCollection<Todo> collection = Database.getCollection("todo", Todo.class);

        // insertOne
        // 단일 insert
        Todo newtodo = new Todo(null, "POJO", "POJO 테스트확인", false);
        collection.insertOne(newtodo);

        // insertMany
        // 다중 insert
        List<Todo> newTodos = Arrays.asList(
                new Todo(null, "POJO2", "POJO2 테스트확인", false),
                new Todo(null, "POJO3", "POJO3 테스트확인", true),
                new Todo(null, "POJO4", "POJO4 테스트확인", false)
        );

        collection.insertMany(newTodos);

        // find()
        // 전체 todo 목록 출력
        List<Todo> todos = new ArrayList<>();
        collection.find().into(todos);

        for (Todo todo : todos) {
            System.out.println(todo);
        }

        // findOne()
        // 특정 id값 기반으로 검색 후 출력
        String id = "666a6296f4fe57189cd03eea";
        Bson query = eq("_id", new ObjectId(id));

        Todo todo = collection.find(query).first();
        System.out.println("==> findByIdResult : " + todo);

        Database.close();
    }
}

