package im.tox.toktok.app.message_activity

import android.content.Intent
import android.graphics.PixelFormat
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.TypedValue
import android.view.View.OnClickListener
import android.view.ViewGroup.LayoutParams
import android.view.{ MenuItem, View, WindowManager }
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration
import im.tox.toktok.TypedResource._
import im.tox.toktok.TypedBundleKey._
import im.tox.toktok.app.call.CallActivity
import im.tox.toktok.app.main.friends.{ FriendItemClicks, FriendsRecyclerHeaderAdapter }
import im.tox.toktok.app.domain.Friend
import im.tox.toktok.app.new_message.NewMessageActivity
import im.tox.toktok.{ BundleKey, R, TContext, TR }

import scala.collection.mutable.ListBuffer

final class MessageGroupContacts extends AppCompatActivity with FriendItemClicks {

  private var adapter: FriendsRecyclerHeaderAdapter = null
  private var colorPrimary: Int = 0
  private var colorStatus: Int = 0

  protected override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_message_group_members)

    val bundle = getIntent.getExtras

    colorPrimary = bundle(BundleKey.colorPrimary)
    colorStatus = bundle(BundleKey.colorPrimaryStatus)

    val mToolbar = this.findView(TR.message_group_members_toolbar)
    mToolbar.setTitle(getResources.getString(R.string.message_group_contacts))
    mToolbar.setBackgroundColor(colorPrimary)
    getWindow.setStatusBarColor(colorStatus)

    setSupportActionBar(mToolbar)
    getSupportActionBar.setDisplayHomeAsUpEnabled(true)

    val mFAB = this.findView(TR.message_group_members_fab)

    mFAB.setOnClickListener(new OnClickListener {
      override def onClick(v: View): Unit = {

        val addContactView: Intent = new Intent(MessageGroupContacts.this, classOf[NewMessageActivity])
        addContactView.putExtras(bundle)
        startActivity(addContactView)

      }
    })

    val mRecycler = this.findView(TR.message_group_members_recycler)

    val friends = ListBuffer(Friend.lorem, Friend.john)

    val mLayoutManager = new LinearLayoutManager(getBaseContext)
    mRecycler.setLayoutManager(mLayoutManager)

    adapter = new FriendsRecyclerHeaderAdapter(friends, this)
    mRecycler.setAdapter(adapter)
    mRecycler.addItemDecoration(new StickyRecyclerHeadersDecoration(adapter))

  }

  override def onOptionsItemSelected(item: MenuItem): Boolean = {
    item.getItemId match {
      case android.R.id.home =>
        finish()
        true
      case _ =>
        super.onOptionsItemSelected(item)
    }
  }

  override def startOverLayFriend(friendPosition: Int): Unit = {
    val layout = getLayoutInflater.inflate(TR.layout.overlay_contacts)
    val params = new WindowManager.LayoutParams(
      LayoutParams.MATCH_PARENT,
      LayoutParams.MATCH_PARENT,
      WindowManager.LayoutParams.TYPE_APPLICATION_ATTACHED_DIALOG,
      WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
        WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
        WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN |
        WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
      PixelFormat.TRANSLUCENT
    )
    getWindowManager.addView(layout, params)

    val actionBarHeight = {
      val tv = new TypedValue
      if (getTheme.resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
        TypedValue.complexToDimensionPixelSize(tv.data, getResources.getDisplayMetrics)
      } else {
        0
      }
    }

    layout.start(this, adapter.getItem(friendPosition), actionBarHeight)
  }

  override def startCall(friendPosition: Int): Unit = {
    val friend = adapter.getItem(friendPosition)

    val bundle = new Bundle
    bundle(BundleKey.contactName) = friend.userName
    bundle(BundleKey.contactColorPrimary) = friend.color
    bundle(BundleKey.contactPhotoReference) = friend.photoReference

    val newIntent = new Intent(this, classOf[CallActivity])
    newIntent.putExtras(bundle)
    startActivity(newIntent)
  }

  override def startMessage(friendPosition: Int): Unit = {
    val friend = adapter.getItem(friendPosition)

    val bundle = new Bundle
    bundle(BundleKey.messageTitle) = friend.userName
    bundle(BundleKey.contactColorPrimary) = friend.color
    bundle(BundleKey.contactColorStatus) = friend.secondColor
    bundle(BundleKey.imgResource) = friend.photoReference
    bundle(BundleKey.messageType) = 0

    val newIntent = new Intent(this, classOf[MessageActivity])
    newIntent.putExtras(bundle)
    startActivity(newIntent)
  }

}