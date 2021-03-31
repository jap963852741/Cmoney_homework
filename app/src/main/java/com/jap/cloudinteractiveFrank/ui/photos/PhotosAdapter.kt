package com.jap.cloudinteractiveFrank.ui.photos

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.jap.cloudinteractiveFrank.R
import com.jap.cloudinteractiveFrank.Repository.bean.Photos
import com.jap.cloudinteractiveFrank.databinding.ItemPhotosBinding
import com.jap.cloudinteractiveFrank.util.LruCacheUtil
import com.jap.cloudinteractiveFrank.util.URLtoBitmapUtil
import java.net.URL


class PhotosAdapter(
    private val dataList: ArrayList<Photos>,
    private val parentview: ViewGroup
) :
    RecyclerView.Adapter<VH>() {
    val TAG = "PhotosAdapter"
    private lateinit var binding : ItemPhotosBinding
    val lruCacheUtil = LruCacheUtil.instance
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        binding = ItemPhotosBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.image1.tag = dataList[position*4]
        holder.image2.tag = dataList[position*4+1]
        holder.image3.tag = dataList[position*4+2]
        holder.image4.tag = dataList[position*4+3]
        update(holder.itemView,holder.image1,holder.textViewId1,holder.textViewTitle1)
        update(holder.itemView,holder.image2,holder.textViewId2,holder.textViewTitle2)
        update(holder.itemView,holder.image3,holder.textViewId3,holder.textViewTitle3)
        update(holder.itemView,holder.image4,holder.textViewId4,holder.textViewTitle4)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun update(itemView : View, image: ImageView, textViewId:TextView, textViewTitle:TextView){
        val uiHandler: Handler = object : Handler(Looper.getMainLooper()){
            override fun handleMessage(msg: Message) {
                when (msg.what) {
                    1 -> {
                        image.layoutParams.height = itemView.width/4
                        if (msg.obj != null)
                            image.setImageBitmap(msg.obj as Bitmap)
                        val photo = image.tag as Photos
                        textViewId.text = photo.id
                        textViewTitle.text = photo.title
                    }
                }
            }
        }
        val photo = image.tag as Photos
        image.setImageDrawable(parentview.resources.getDrawable(R.drawable.ic_refresh,null))
        var bitmap = lruCacheUtil.getBitmap(photo.thumbnailUrl)
        if (bitmap == null) {
            URLtoBitmapUtil.instance.get(URL(photo.thumbnailUrl)
                , object : URLtoBitmapUtil.URLtoBitmapTaskFinish {
                    override fun onFinish(data: Bitmap?) {
                        if( data != null) {
                            val msg = Message()
                            msg.what = 1
                            msg.obj = data
                            uiHandler.sendMessage(msg)
                            lruCacheUtil.putBitmap(photo.thumbnailUrl, data)
                        }
                    }
                })
        }else{
            val msg = Message()
            msg.what = 1
            msg.obj = bitmap
            uiHandler.sendMessage(msg)
        }

        image.setOnClickListener {
            it.findNavController().navigate(PhotosFragmentDirections.goDetail().setId(photo.id)
                .setTitle(photo.title)
                .setUrl(photo.url)
            )
        }
    }

}


class VH(binding: ItemPhotosBinding) :
    RecyclerView.ViewHolder(binding.root) {
    var image1 : ImageView = binding.iv1.imageView
    var textViewId1 : TextView = binding.iv1.textViewId
    var textViewTitle1 : TextView = binding.iv1.textViewTitle

    var image2 : ImageView = binding.iv2.imageView
    var textViewId2 : TextView = binding.iv2.textViewId
    var textViewTitle2 : TextView = binding.iv2.textViewTitle

    var image3 : ImageView = binding.iv3.imageView
    var textViewId3 : TextView = binding.iv3.textViewId
    var textViewTitle3 : TextView = binding.iv3.textViewTitle

    var image4 : ImageView = binding.iv4.imageView
    var textViewId4 : TextView = binding.iv4.textViewId
    var textViewTitle4 : TextView = binding.iv4.textViewTitle
}