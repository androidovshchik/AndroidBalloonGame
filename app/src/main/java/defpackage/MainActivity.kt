package defpackage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.frameLayout

class MainActivity : AppCompatActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        frameLayout {
            gameSurfaceView()
        }
    }
}