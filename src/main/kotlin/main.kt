import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import java.io.FileInputStream


fun main() {
    val options = FirebaseOptions.builder()
        .setCredentials(GoogleCredentials.fromStream(FileInputStream("fcm.json")))
        .build()

    FirebaseApp.initializeApp(options)

    val token = "cb2HiQOfQRCl13Aq84fD9z:APA91bFevjGbo6aFgGJD29SdcceL9BiTUz0vXPqR-HHEGYqvo6rHTwz21wzFZ6YTQQONtx2y9cMWxlGgpZ5n4ijnGkVtFy3oVu-vld98sm_T-g7TpDq_7iGI0GfTlBqivzr_s00YvfW4"

    println("""Введите нужный вариант
         1: Отправить сообщение с Like
         2: Отправить оповещение о новом посте
         (для проверки не соответствующей нотификации введите любой текст)""".trimMargin())
    val message = when(val string = checkMyReadLine()) {
        "1" -> {
            Message.builder()
                .putData("action", "LIKE")
                .putData(
                    "content", """{
                    "userId": 1,
                    "userName": "Vasiliy",
                    "postId": 2,
                    "postAuthor": "Netology"
                    }""".trimIndent()
                )
                .setToken(token)
                .build()
        }
        "2" -> {
            Message.builder()
                .putData("action", "NEW_POST")
                .putData(
                    "content", """{
                    "userId": 1,
                    "userName": "Vasiliy",
                    "postId": 2,
                    "postContent": "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растем сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия - помочь встать на путь роста и начать цепочку перемен http://netolo.gy/fyb"
                    }""".trimIndent()
                )
                .setToken(token)
                .build()
        }
        else -> {
            Message.builder()
                .putData("action", string)
                .putData(
                    "content", """{
                    "userId": 1,
                    "userName": "Vasiliy",
                    "postId": 2,
                    "content": $string
                    }""".trimIndent()
                )
                .setToken(token)
                .build()
        }
    }
    FirebaseMessaging.getInstance().send(message)
}
fun checkMyReadLine(): String {
    val myString:String = readLine()!!
    return myString.ifEmpty { "empty" }
}

