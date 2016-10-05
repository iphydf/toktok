package im.tox.toktok.app.SimpleDialogs

import android.app.{Activity, Dialog}
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v7.widget.{LinearLayoutManager, RecyclerView}
import android.view.View.OnClickListener
import android.view.{View, Window}
import android.widget._
import im.tox.toktok.R
import im.tox.toktok.app.MaterialColors

class SimpleColorDialogDesign(activity: Activity, title: String, contact_color: Int, icon: Int, color: Int, clickAction: OnClickListener) extends Dialog(activity, R.style.DialogSlideAnimation) {

  var a: Activity = activity

  override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)
    requestWindowFeature(Window.FEATURE_NO_TITLE)
    setContentView(R.layout.simple_color_dialog_design)
    getWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT))


    findViewById(R.id.simple_dialog_color).asInstanceOf[LinearLayout].setBackgroundTintList(ColorStateList.valueOf(contact_color))
    findViewById(R.id.simple_dialog_img).asInstanceOf[ImageView].setImageResource(icon)
    findViewById(R.id.simple_dialog_text).asInstanceOf[TextView].setText(title)

    val color_recycler: RecyclerView = findViewById(R.id.simple_color_dialog_recyclerview).asInstanceOf[RecyclerView]

    val colors: MaterialColors = new MaterialColors
    color_recycler.setAdapter(new SimpleColorDialogDesignAdapter(colors.getColors()))
    color_recycler.setLayoutManager(new LinearLayoutManager(activity))



    val cancelButton = findViewById(R.id.simple_dialog_cancel).asInstanceOf[Button]
    cancelButton.setOnClickListener(new OnClickListener {
      override def onClick(v: View): Unit = {
        dismiss()
      }
    })

  }

}
