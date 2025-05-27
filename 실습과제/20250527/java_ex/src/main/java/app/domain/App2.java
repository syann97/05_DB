package app.domain;

import app.Database;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertManyResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;



public class App2 {
    public static void main(String[] args) {
        MongoCollection<Todo> collection = Database.getCollection("todo", Todo.class);

        // 1개 삽입
        // insertTodo(collection, "알고리즘 공부", "오늘 어디까지 하기");

         insertManyTodo(collection, "샘플", 5);

//        selectAllTodo(collection);

        // _id 일치하는 Todo의 done 수정
//        updateTodo(collection, "68354343fb6769625c69827e", false);

//        updateAllTodo(collection, true);

        // 삭제
//        deleteTodo(collection, "6835433cc4f20c41b9115634");

        // 전체 삭제
//        deleteAllTodo(collection);

        Database.close();
    }


    /***
     * 단일 Todo 삽입
     * @param collection
     * @param title
     * @param desc
     */
    private static void insertTodo(MongoCollection<Todo> collection, String title, String desc)  {
        Todo todo = new Todo(null, title, desc, false);

        InsertOneResult result = collection.insertOne(todo);
        System.out.println("==> InsertOneResult : " + result.getInsertedId());
    }

    /***
     * 다중 Todo 삽입
     * @param collection
     * @param str
     * @param count
     */
    private static void insertManyTodo(MongoCollection<Todo> collection, String str, int count) {
        // str을 제목, 설명으로 갖는 샘플 (Todo 문서 count 만큼 삽입하기)

        List<Todo> todoList = new ArrayList<>();    // 빈 List 생성
        for (int i = 0; i < count; i++) {
            Todo todo = new Todo(null, str + i, str + i + " 설명", false);
            todoList.add(todo);
        }

        InsertManyResult result = collection.insertMany(todoList);

        System.out.println("샘플 데이터 추가 성공 : " + result.getInsertedIds());
    }


    /***
     * 모든 Todo 조회
     * @param collection
     */
    private static void selectAllTodo(MongoCollection<Todo> collection) {
        List<Todo> todoList = new ArrayList<>(); // 빈 list 생성
        collection.find().into(todoList);

        todoList.forEach(System.out::println);
    }

    /***
     * _id 일치하는 Todo 조회
     * @param collection
     * @param id
     */
    private static void selectTodo(MongoCollection<Todo> collection, String id) {
        // 쿼리 셀렉터 (조건) 생성
        Bson query = Filters.eq("_id", new ObjectId(id));

        // 조회된 문서 1개를 자동으로 Todo로 변환하여 저장
        Todo todo = collection.find(query).first();

        if (todo == null) {
            System.out.println("_id가 일치하는 문서가 없습니다.");
            return;
        }

        System.out.println("_id : " + todo.getId());
        System.out.println("_title : " + todo.getTitle());
        System.out.println("_desc : " + todo.getDesc());
        System.out.println("_done : " + todo.isDone());
    }

    /***
     * id가 일치하는 Todo 문서의 done 값을 수정
     * @param collection
     * @param id
     * @param done
     */
    private static void updateTodo(MongoCollection<Todo> collection, String id, boolean done) {
        // 쿼리 셀렉터(조건) 생성
        Bson query = Filters.eq("_id", new ObjectId(id));

        // 업데이트 할 내용 (set, upset 등)
        Bson update = Updates.combine(Updates.set("done", done));


        // 실행 후 결과 객체(UpdateOne(), UpdateResult)
        // -> 수정된 문서의 개수 반환
        UpdateResult result = collection.updateOne(query, update);


        // 개수 == 0
        // 실패 메시지 출력
        if (result.getModifiedCount() == 0) {
            System.out.println("일치하는 _id를 가지는 문서가 없거나 수정 사항 없음");
            return;
        }

        // 개수 > 0
        // 성공 메시지 출력 + selectTodo(collection, id) 호출
        System.out.println("=== 수정 성공 ===");
        selectTodo(collection, id);

    }


    /***
     * 모든 Todo 문서의 done 값 변경
     * @param collection
     * @param done
     */
    private static void updateAllTodo(MongoCollection<Todo> collection, boolean done) {
        //db.todo.updateMany({}, {$set, {"done": true}})

        Bson query = Filters.empty(); // 빈 조건 생성 (비어있는 쿼리 셀렉터 {} 생성)

        Bson update = Updates.set("done", done);

        UpdateResult result = collection.updateMany(query, update);

        if (result.getModifiedCount() == 0) {
            System.out.println("변경 사항 없음");
            return;
        }

        System.out.println("=== 일괄 수정 성공 ===");
        selectAllTodo(collection);
    }


    /***
     * _id가 일치하는 Todo 문서 삭제
     * @param collection
     * @param id
     */
    private static void deleteTodo(MongoCollection<Todo> collection, String id) {

        Bson query = Filters.eq("_id", new ObjectId(id));
        DeleteResult result = collection.deleteOne(query);


        if (result.getDeletedCount() == 0) {
            System.out.println("일치하는 _id 없음");
            return;
        }

        System.out.println("=== 삭제 성공 ===");
    }


    /***
     * 모든 Todo 문서를 삭제
     * @param collection
     */
    private static void deleteAllTodo (MongoCollection<Todo> collection) {

        Bson query = Filters.empty();   // 조건 없음

        DeleteResult result = collection.deleteMany(query); // 모든 문서 삭제

        System.out.println("삭제된 문서의 개수 : " + result.getDeletedCount());
    }
}
