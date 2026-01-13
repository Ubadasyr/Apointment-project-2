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
-  register (/Auth/register) 
-  Login (/Auth/Login )
-  user appointment (/Auth/myappoinments) for customer and staff
- get services (/ser/get)
- get available appointment (/Appoinment/get/Available)
- Book appointment (/Appoinment/Book)
- customer cancel appointment (/Auth/cancel)
- notifications (/notification/unseen)



- ------
* Admin APIS:
- create service (/ser/create)
- update service (/ser/update/{ser_id})
- get all appointments (/get/appointments)
- Accept Appointment (/Admin/approve)
- Reject Appointment (/Admin/reject)
- update Appointment status (/Admin/update_status)
-------"# Apointment-project-2" 
