package alphasoft.diceroller.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import alphasoft.diceroller.R
import alphasoft.diceroller.Utils.Screenshot
import alphasoft.diceroller.showToast
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import androidx.core.os.postDelayed
import kotlinx.android.synthetic.main.activity_main.*
import java.io.InputStream
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnRollDie.setOnClickListener {
            RollDice()
        }
    }

    private fun RollDice() {
        val num:String=(Random().nextInt(6)+1).toString()
        textDice.text="You got $num on the roll!"
        val image=when(num.toInt()){
            1->R.drawable.dice_1
            2->R.drawable.dice_2
            3->R.drawable.dice_3
            4->R.drawable.dice_4
            5->R.drawable.dice_5
            else-> R.drawable.dice_6
        }
        imageView.setImageResource(image)
    }
}
