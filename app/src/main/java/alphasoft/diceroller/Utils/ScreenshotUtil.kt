package alphasoft.diceroller.Utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Environment
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import alphasoft.diceroller.showToast
import android.graphics.*
import android.text.style.TtsSpan
import java.io.File
import java.io.FileOutputStream
import java.sql.Timestamp
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*


private fun setupPermissions(activity:Activity) {
    val permission = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)

    if (permission != PackageManager.PERMISSION_GRANTED) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            val builder = AlertDialog.Builder(activity)
            builder.setMessage("Permission to save the image")
                .setTitle("Permission required")

            builder.setPositiveButton("OK"
            ) { dialog, id ->
                Log.i("ZOK", "Clicked")
                makeRequest(activity)
            }

            val dialog = builder.create()
            dialog.show()
        } else {
            makeRequest(activity)
        }
    }
}
private fun makeRequest(activity: Activity) {
    val RECORD_REQUEST_CODE=112
    ActivityCompat.requestPermissions(activity,
        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), RECORD_REQUEST_CODE
    )
}

fun getBitmapFromView(view: View):Bitmap {
    //Define a bitmap with the same size as the view
    val returnedBitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888);


    //Bind a canvas to it
    val canvas = Canvas(returnedBitmap);
    //Get the view's background
    val bgDrawable = view.background;
    if (bgDrawable != null)
    //has background drawable, then draw it on the canvas
        bgDrawable.draw(canvas);
    else
    //does not have background drawable, then draw white background on the canvas
    canvas.drawRGB(255,255,255)
    // draw the view on the canvas
    view.draw(canvas);
    //return the bitmap
    return returnedBitmap;
}


fun Screenshot(activity: Activity,context: Context,view: View) :String{

    var uriStr:String=""
    val permission = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)

    if (permission != PackageManager.PERMISSION_GRANTED)
    {
        setupPermissions(activity)
        context.showToast("Please grant permissions")
    }else
    {
        var bitmap: Bitmap = getBitmapFromView(view)
        uriStr= saveImage(bitmap!!)

    }
    return uriStr
}




fun saveImage(bitmap:Bitmap):String{
    val saveDir= Environment.getExternalStorageDirectory().toString()
    val dir= File("$saveDir/ProjImage")

    dir.mkdir()
    val fileName="img${(System.currentTimeMillis()/1000).toString()}.jpg"
    

    val file = File(dir, fileName)
    val uriStr= "$dir/$fileName"
    val out = FileOutputStream(file)
    bitmap.compress(Bitmap.CompressFormat.JPEG, 85,out)
    out.flush()
    out.close()

    return uriStr

}


