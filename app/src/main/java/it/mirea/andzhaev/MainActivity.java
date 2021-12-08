package it.mirea.andzhaev;

import static java.sql.DriverManager.println;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_PERMISSION_READ_CONTACTS = 1;
    // уведомления
    private Button buttonNotify;
    private NotificationManager nm;
    public static final String CAHNNEL_ID = "default_channel_id";
    private final int Notification_Id = 1;// уникальность уведомления

    //камера


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonNotify = findViewById(R.id.buttonNotify);
        nm = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        actionBar();

        int permissionStatus = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);

        if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
            readContacts();
        } else {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_CONTACTS},
                    REQUEST_CODE_PERMISSION_READ_CONTACTS);
        }


    }

    private void readContacts() {
    }

    public void makeText(View view) {
        buttonNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // показать текст
                show();
                Toast.makeText(getApplicationContext(), "Уведомление получено", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void actionBar() { // скрыть
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    public void show() {
        //Notification.Builder builder = new Notification.Builder(getApplicationContext()); // позволит хар нашего уведомления
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT); // запускается, при нажатии на уведомление
        //FLAG_UPDATE_CURRENT - если было старое уведомление, то должно создаваться новое
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(getApplicationContext(), CAHNNEL_ID)
                        .setContentIntent(pendingIntent) // откроет активити, при tape
                        .setSmallIcon(R.drawable.ic_launcher_background) // иконка
                        .setTicker("У вас новое уведомление \uD83E\uDD7A") // сверху уведомление
                        .setContentTitle("ИКБО-06-20") // Заголовок уведомления
                        .setContentText("Анджаев Батнасан Мукобенович")
                        .setWhen(System.currentTimeMillis()) // время
                        .setAutoCancel(true); // чтобы закрывался автоматически

        Notification notification = notificationBuilder.build(); // сборка проектов
        createChannelIfNeeded(nm);
        nm.notify(Notification_Id, notification);


    }

    public static void createChannelIfNeeded(NotificationManager manager) { // метод от 8 версий андроид
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CAHNNEL_ID, CAHNNEL_ID, NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(notificationChannel);
        }
    }


    public void viewCamera(View view) {
        Intent intent = new Intent(this, CameraActivity.class);
        Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivity(intent);
        startActivity(intent1);

    }

}