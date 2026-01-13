التقنيات المستخدمة:
JAVA JDK 17
SPRING BOOT
SPRING WEB
SPRING DATA JPA
SPRING SECURITY (JWT)
WEBSOCKET
H2 DATA BASE
MAVEN
POST MAN
APACHE JMETER
خطوات تشغيل المشروع:
1) التاكد من تثبيت 
 - Java 17 (JDK 17)
 - Maven
2) يعمل على السيرفر
  -https://localhost:8443
3) الدخول إلى H2 Console
-https://localhost:8443/h2-console
 - JDBC URL:
jdbc:h2:file:C:/Users/LENOVO/servcie4
هذا المسار يعتمد على ملفات حاسوبك غيره على حسب ملفاتك  
يمكنك تغييره من application.properties
 
4) بيانات الدخول (seeder)
 - Admin Email: admin@admin.com
 - Admin Password: 123456789
ملاحظة: نسنخدمه لانشاء الخدمات او قبول المواعيد و تغيير حالتها

 
ملاحظة : استخدمت STOMP WebSocket Client Chrome Ext لكي نفحص websocket عند انشاء الاشعارات يتم رابط الويب سوكت (https://localhost:8443/ws) و لرؤية الاشعار (/notify/private.user_id)




شرح بنية النظام :
النظام بتبع نمط CONTROLLER - SERVICES -REPOSITORY-ENTITY

 # Model
 - users
 - services
 - service_staff
 - appointments
 - work_schedule
 - notifications

تمثل هذه الكيانات بنية قاعدة البيانات والعلاقات بينها


___
# Repository
 • التعامل مع قاعدة البيانات باستخدام Spring Data JPA
 • توفير توابع جاهزة (CRUD + Custom Queries)

___

 # Service
 • تحتوي على منطق العمل (Business Logic)
 - إدارة:
 - حجز المواعيد
 - التحقق من التوافر
 - تغيير حالة الموعد
 - إرسال الإشعارات

___

# Controller
 - استقبال طلبات HTTP
 - إعادة النتائج بصيغة JSON
 - فصل منطق الـ HTTP عن منطق العمل

___

 # AOP
 - تسجيل (Logging) تنفيذ التوابع
 - تتبع زمن التنفيذ
 - التعامل مع الأخطاء العامة

___
وصف عمل النظام :
 - يقوم المستخدم بحجز موعد لخدمة معينة
 - يتم التحقق من:
 - دوام الموظف
 - عدم وجود تعارض في المواعيد
 - يتم إنشاء الموعد بحالة PENDING
 - يتم إرسال إشعار للإدارة
 - تقوم الإدارة بالموافقة أو الرفض
 - يتم تحديث حالة الموعد وإرسال إشعار للمستخد
تقسيم العمل :
1) CORE BACKEND -> عبادة عبد السلام
2) SPRING SECURITY -> محمد ياسر دياب
3) HTTPS and AOP-> ضياء عبدو
4) WEBSOCKET -> عبد الواحد ابو خرمة
5) STRESS TEST -> مايك خربوتلي

# APIs 
-  register (/Auth/register) :
.  Post
. Request : {
"fullName": "obada",
"email" : "obada2@mail.com",
"password": "12345678"
}
. Response : "Registered"

------------------
-  Login (/Auth/Login )
. Post
. Request : {
"email" : "obada2@mail.com",
"password": "12345678"
}
. Response : {
    "Token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzdGFmZjFAZXhhbXBsZS5jb20iLCJjcmVhdGVkIjoxNzY4MzE5OTYyODI1LCJleHAiOjE3Njg0MDYzNjJ9.8YW36NatGvpkYcOXxQA-aO7K683UqL9kdw41uXXvDNY",
    "message": "login done",
    "username": "obada",
    "ID": 1
}
----------------------
-  user appointment (/Auth/myappoinments) for customer and staff
. GET
.Response : [ {
        "id": 1,
        "serviceId": 148,
        "serviceName": "Mobile App Development 25",
        "staffId": 18,
        "staffName": "Staff 3",
        "customerId": 1,
        "customerName": "Ahmad Staff",
        "status": "PENDING",
        "startTime": "2026-12-28T10:00:00",
        "endTime": "2026-12-28T10:35:00"
    }]

----------------------------

- get services (/ser/get)
GET
.Response:
[
    {
        "serviceId": 1,
        "serviceName": "Mobile App Development 18",
        "durationMinutes": 110,
        "price": 50000.0,
        "staff": [
            {
                "id": 11,
                "fullName": "Staff 1"
            }]


---------------------------
- get available appointment (/Appoinment/get/Available)
. GET
. Request:
  Params : ser_id : 1
           date   : 2026-01-01
           Staff_id : 1
  Response :[
    {
        "start": "2026-01-01T11:30:00",
        "end": "2026-01-01T12:00:00"
    },
    {
        "start": "2026-01-01T12:00:00",
        "end": "2026-01-01T12:30:00"
    }]

-----------------------
- Book appointment (/Appoinment/Book)
 . GET
 . Requset :
  Params :  ser_id : 1
           date   : 2026-01-01T11:30:00
           Staff_id : 1

Response : "Appoinment is Pending "

-------------------------

- customer cancel appointment (/Auth/cancel)
. GET
. Request :
Params : Appid : 1
. Response : "Appointment is Cancelled"
-----------------------

- notifications (/notification/unseen)
  . GET
. Response :  "MESAAGE:": [
        "New appointment pending ",
        "Your appointment is FINISHED"]

- ---------
* Admin APIS:
- create service (/ser/create)
 . POST
  . Requset :
  {
  "name": "Web dev",
  "durationMinutes": 30,
  "price": 50,
  "staffIds": [11]
. Response : "Service created with staff"

---------
- update service (/ser/update/{ser_id})
 . POST
  . Requset :
  {
  "name": "Web dev",
  "durationMinutes": 30,
  "price": 50,
  "staffIds": [11]
. Response :   "Service updated"
------------------
- get all appointments (/get/appointments)
. GET
. Request :
  . Params : date : 2026-01-01
. Respones : [
    {
        "ID": 8,
        "start": "2026-01-01T14:30:00",
        "end": "2026-01-01T15:05:00",
        "Staff_id": 18,
        "Staff_name": "Staff 3",
        "customer_id": 1,
        "customer_name": "Ahmad Staff",
        "service_title": " web dev ",
        "status": "PENDING"
    }]  
  
-----------
- Accept Appointment (/Admin/approve)
. GET
. Request :
   .Params:
      Appid : 1
 .Response :    "Appointment is approved"

--------------
- Reject Appointment (/Admin/reject)
. GET
. Request :
   .Params:
      Appid : 1
 .Response :    "Appointment is Canceled"


- update Appointment status (/Admin/update_status)
. POST
. Request :{
       "Appid": 62,
       "STATUS":APPROVED
  }
. Response :"Status updated to APPROVED"
    
  









-------"# Apointment-project-2" 
