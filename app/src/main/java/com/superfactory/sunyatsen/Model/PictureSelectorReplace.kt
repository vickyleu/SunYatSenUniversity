package com.superfactory.sunyatsen.Model

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v4.app.Fragment
import com.luck.picture.lib.PictureExternalPreviewActivity
import com.luck.picture.lib.PicturePlayAudioActivity
import com.luck.picture.lib.PictureVideoPlayActivity
import com.luck.picture.lib.R.anim
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.tools.DoubleUtils
import java.lang.ref.WeakReference

/**
 * Created by vicky on 2018/2/6.
 */
open class PictureSelectorReplace {
    private var mActivity: WeakReference<Activity>? = null;
    private var mFragment: WeakReference<Fragment?>? = null;

    private constructor(activity: Activity) : this(activity, null)

    private constructor(fragment: Fragment) : this(fragment.activity!!, fragment)

    constructor(activity: Activity, fragment: Fragment?) {
        this.mActivity = WeakReference(activity)
        this.mFragment = WeakReference(fragment)
    }

    companion object {
        fun create(activity: Activity): PictureSelectorReplace {
            return PictureSelectorReplace(activity)
        }

        fun create(activity: Fragment): PictureSelectorReplace {
            return PictureSelectorReplace(activity)
        }

        fun obtainMultipleResult(data: Intent?): List<LocalMedia> {
            val result = arrayListOf<LocalMedia>()
            if (data != null) {
                var result1 = data.getSerializableExtra("extra_result_media") as? List<LocalMedia>
                if (result1 == null) {
                    result1 = arrayListOf<LocalMedia>()
                }

                return result;
            } else {
                return result;
            }
        }

        fun putIntentResult(data: List<LocalMedia>): Intent {
            return (Intent()).putExtra("extra_result_media", data as java.io.Serializable);
        }

        fun obtainSelectorList(bundle: Bundle?): List<LocalMedia> {
            if (bundle != null) {
                return bundle.getSerializable("selectList") as List<LocalMedia>;
            } else {
                return arrayListOf<LocalMedia>()
            }
        }

        fun saveSelectorList(outState: Bundle, selectedImages: List<LocalMedia>): Unit {
            outState.putSerializable("selectList", selectedImages as java.io.Serializable);
        }


    }


    open fun openGallery(mimeType: Int): PictureSelectionModelReplace {
        return PictureSelectionModelReplace(this, mimeType);
    }

    public fun openCamera(mimeType: Int): PictureSelectionModelReplace {
        return PictureSelectionModelReplace(this, mimeType, true);
    }


    open fun externalPicturePreview(position: Int, medias: List<LocalMedia>) {
        if (!DoubleUtils.isFastDoubleClick()) {
            val intent = Intent(this.getActivity(), PictureExternalPreviewActivity::class.java)
            intent.putExtra("previewSelectList", medias as java.io.Serializable);
            intent.putExtra("position", position);
            this.getActivity()?.startActivity(intent);
            this.getActivity()?.overridePendingTransition(anim.a5, 0);
        }

    }

    open fun externalPicturePreview(position: Int, directory_path: String, medias: List<LocalMedia>) {
        if (!DoubleUtils.isFastDoubleClick()) {
            val intent = Intent(this.getActivity(), PictureExternalPreviewActivity::class.java)
            intent.putExtra("previewSelectList", medias as java.io.Serializable);
            intent.putExtra("position", position);
            intent.putExtra("directory_path", directory_path);
            this.getActivity()?.startActivity(intent);
            this.getActivity()?.overridePendingTransition(anim.a5, 0);
        }

    }

    open fun externalPictureVideo(path: String) {
        if (!DoubleUtils.isFastDoubleClick()) {
            val intent = Intent(this.getActivity(), PictureVideoPlayActivity::class.java)
            intent.putExtra("video_path", path);
            this.getActivity()?.startActivity(intent);
        }
    }

    open fun externalPictureAudio(path: String) {
        if (!DoubleUtils.isFastDoubleClick()) {
            val intent = Intent(this.getActivity()!!, PicturePlayAudioActivity::class.java)
            intent.putExtra("audio_path", path);
            this.getActivity()?.startActivity(intent);
            this.getActivity()?.overridePendingTransition(anim.a5, 0);
        }
    }

    @Nullable
    fun getActivity(): Activity? {
        return this.mActivity?.get()
    }

    @Nullable
    fun getFragment(): Fragment? {
        return if (this.mFragment != null) this.mFragment!!.get() as Fragment else null;
    }
}