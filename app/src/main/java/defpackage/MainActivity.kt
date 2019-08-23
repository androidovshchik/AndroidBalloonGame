package defpackage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.frameLayout
import org.jetbrains.anko.matchParent

class MainActivity : AppCompatActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        frameLayout {
            lparams(matchParent, matchParent)
            gameSurfaceView {
                lparams(matchParent, matchParent)
            }
        }
    }
}