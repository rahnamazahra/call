package com.rahnama.callapplication

import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rahnama.callapplication.databinding.ActivityMainBinding
import android.content.pm.PackageManager

import androidx.core.app.ActivityCompat

import android.net.Uri

import android.content.Intent
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity


private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.call.setOnClickListener {
            checkPermission()

        }
    }

    /******************************************/
    fun checkPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CALL_PHONE
            )
            != PackageManager.PERMISSION_GRANTED
        ) {

            /*
            متد shouldShowRequestPermissionRationale
            با استفاده از این متد می‌توانیم تعیین کنیم اگر کاربر قبلا یک بار درخواست مجوزی را رد کرده، برای دفعات بعد،
             قبل از درخواست مجدد مجوز ابتدا یک پیغام حاوی توضیحات لازم نیز نمایش داده شود. کاربرد این متد از ترجمه تحت الفظی نام آن نیز مشخص می‌شود: “باید علت درخواست مجوز نمایش داده شود”. فکر می‌کنم حالا ماهیت شرطی که تعریف شده برایتان روشن شده. در اینجا تعریف کردیم اگر shouldShowRequestPermissionRationale مقدار true برگرداند، به عبارتی اگر لازم است توضیحاتی به کاربر ارائه شود، قسمت اول شرط و در غیر اینصورت قسمت دوم شرط را اجرا شود.

             */
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)) {

                val alert: AlertDialog.Builder = AlertDialog.Builder(this)

                    alert.setTitle("درخواست مجوز")
                    .setMessage("برای برقراری تماس باید مجوز را تایید کنید")
                    .setPositiveButton("موافقم",  DialogInterface.OnClickListener {
                            _, _ -> finish()
                        ActivityCompat.requestPermissions(
                            this,
                            arrayOf(Manifest.permission.CALL_PHONE),
                            42
                        )

                    })
                    .setNegativeButton("لغو", DialogInterface.OnClickListener {
                            _, _ -> finish()


                    })
                    .create()
                    .show()

            } else {

                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CALL_PHONE),
                    42
                )
            }
        } else {
            //"مجوز قبلا دریافت شده"
            callPhone()
        }
    }

    /****************************************/
    override fun onRequestPermissionsResult(

        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 42) {
            // If request is cancelled, the result arrays are empty.
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                //واژه Granted یعنی “موافقت شده”.
                // بنابراین هنگامی که مجوز مدنظر قبلا تایید شده باشد این مقدار را برمی‌گرداند.
                callPhone()
            } else {
                   // PERMISSION_DENIED:یعنی “رد شده”
                 // . بنابراین هنگامی که مجوز مدنظر قبلا تایید نشده باشد این مقدار را برمی‌گرداند.

            }
            return
        }
    }

    /*************************************************/
    fun callPhone() {
        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "1122334455"))
        startActivity(intent)
    }
}