package com.superfactory.sunyatsin.Model

import android.content.Intent
import android.support.annotation.FloatRange
import android.support.annotation.IntRange
import android.support.annotation.StyleRes
import com.luck.picture.lib.PictureSelectorActivity
import com.luck.picture.lib.R
import com.luck.picture.lib.config.PictureSelectionConfig
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.tools.DoubleUtils
import java.util.*

/**
 * Created by vicky on 2018/2/6.
 */

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

class PictureSelectionModelReplace {
    private var selectionConfig: PictureSelectionConfig? = null
    private var selector: PictureSelectorReplace? = null

    constructor(selector: PictureSelectorReplace, mimeType: Int) {
        this.selector = selector
        this.selectionConfig = PictureSelectionConfig.getCleanInstance()
        this.selectionConfig!!.mimeType = mimeType
    }

    constructor(selector: PictureSelectorReplace, mimeType: Int, camera: Boolean) {
        this.selector = selector
        this.selectionConfig = PictureSelectionConfig.getCleanInstance()
        this.selectionConfig!!.camera = camera
        this.selectionConfig!!.mimeType = mimeType
    }

    fun theme(@StyleRes themeStyleId: Int): PictureSelectionModelReplace {
        this.selectionConfig!!.themeStyleId = themeStyleId
        return this
    }

    fun selectionMode(selectionMode: Int): PictureSelectionModelReplace {
        this.selectionConfig!!.selectionMode = selectionMode
        return this
    }

    fun enableCrop(enableCrop: Boolean): PictureSelectionModelReplace {
        this.selectionConfig!!.enableCrop = enableCrop
        return this
    }

    fun enablePreviewAudio(enablePreviewAudio: Boolean): PictureSelectionModelReplace {
        this.selectionConfig!!.enablePreviewAudio = enablePreviewAudio
        return this
    }

    fun freeStyleCropEnabled(freeStyleCropEnabled: Boolean): PictureSelectionModelReplace {
        this.selectionConfig!!.freeStyleCropEnabled = freeStyleCropEnabled
        return this
    }

    fun scaleEnabled(scaleEnabled: Boolean): PictureSelectionModelReplace {
        this.selectionConfig!!.scaleEnabled = scaleEnabled
        return this
    }

    fun rotateEnabled(rotateEnabled: Boolean): PictureSelectionModelReplace {
        this.selectionConfig!!.rotateEnabled = rotateEnabled
        return this
    }

    fun circleDimmedLayer(circleDimmedLayer: Boolean): PictureSelectionModelReplace {
        this.selectionConfig!!.circleDimmedLayer = circleDimmedLayer
        return this
    }

    fun showCropFrame(showCropFrame: Boolean): PictureSelectionModelReplace {
        this.selectionConfig!!.showCropFrame = showCropFrame
        return this
    }

    fun showCropGrid(showCropGrid: Boolean): PictureSelectionModelReplace {
        this.selectionConfig!!.showCropGrid = showCropGrid
        return this
    }

    fun hideBottomControls(hideBottomControls: Boolean): PictureSelectionModelReplace {
        this.selectionConfig!!.hideBottomControls = hideBottomControls
        return this
    }

    fun withAspectRatio(aspect_ratio_x: Int, aspect_ratio_y: Int): PictureSelectionModelReplace {
        this.selectionConfig!!.aspect_ratio_x = aspect_ratio_x
        this.selectionConfig!!.aspect_ratio_y = aspect_ratio_y
        return this
    }

    fun maxSelectNum(maxSelectNum: Int): PictureSelectionModelReplace {
        this.selectionConfig!!.maxSelectNum = maxSelectNum
        return this
    }

    fun minSelectNum(minSelectNum: Int): PictureSelectionModelReplace {
        this.selectionConfig!!.minSelectNum = minSelectNum
        return this
    }

    fun videoQuality(videoQuality: Int): PictureSelectionModelReplace {
        this.selectionConfig!!.videoQuality = videoQuality
        return this
    }

    fun imageFormat(suffixType: String): PictureSelectionModelReplace {
        this.selectionConfig!!.suffixType = suffixType
        return this
    }

    fun cropWH(cropWidth: Int, cropHeight: Int): PictureSelectionModelReplace {
        this.selectionConfig!!.cropWidth = cropWidth
        this.selectionConfig!!.cropHeight = cropHeight
        return this
    }

    fun videoMaxSecond(videoMaxSecond: Int): PictureSelectionModelReplace {
        this.selectionConfig!!.videoMaxSecond = videoMaxSecond * 1000
        return this
    }

    fun videoMinSecond(videoMinSecond: Int): PictureSelectionModelReplace {
        this.selectionConfig!!.videoMinSecond = videoMinSecond * 1000
        return this
    }

    fun recordVideoSecond(recordVideoSecond: Int): PictureSelectionModelReplace {
        this.selectionConfig!!.recordVideoSecond = recordVideoSecond
        return this
    }

    fun glideOverride(@IntRange(from = 100L) width: Int, @IntRange(from = 100L) height: Int): PictureSelectionModelReplace {
        this.selectionConfig!!.overrideWidth = width
        this.selectionConfig!!.overrideHeight = height
        return this
    }

    fun sizeMultiplier(@FloatRange(from = 0.10000000149011612) sizeMultiplier: Float): PictureSelectionModelReplace {
        this.selectionConfig!!.sizeMultiplier = sizeMultiplier
        return this
    }

    fun imageSpanCount(imageSpanCount: Int): PictureSelectionModelReplace {
        this.selectionConfig!!.imageSpanCount = imageSpanCount
        return this
    }

    fun minimumCompressSize(size: Int): PictureSelectionModelReplace {
        this.selectionConfig!!.minimumCompressSize = size
        return this
    }

    fun cropCompressQuality(compressQuality: Int): PictureSelectionModelReplace {
        this.selectionConfig!!.cropCompressQuality = compressQuality
        return this
    }

    fun compress(isCompress: Boolean): PictureSelectionModelReplace {
        this.selectionConfig!!.isCompress = isCompress
        return this
    }

    fun synOrAsy(synOrAsy: Boolean): PictureSelectionModelReplace {
        this.selectionConfig!!.synOrAsy = synOrAsy
        return this
    }

    fun compressSavePath(path: String): PictureSelectionModelReplace {
        this.selectionConfig!!.compressSavePath = path
        return this
    }

    fun isZoomAnim(zoomAnim: Boolean): PictureSelectionModelReplace {
        this.selectionConfig!!.zoomAnim = zoomAnim
        return this
    }

    fun previewEggs(previewEggs: Boolean): PictureSelectionModelReplace {
        this.selectionConfig!!.previewEggs = previewEggs
        return this
    }

    fun isCamera(isCamera: Boolean): PictureSelectionModelReplace {
        this.selectionConfig!!.isCamera = isCamera
        return this
    }

    fun setOutputCameraPath(outputCameraPath: String): PictureSelectionModelReplace {
        this.selectionConfig!!.outputCameraPath = outputCameraPath
        return this
    }

    fun isGif(isGif: Boolean): PictureSelectionModelReplace {
        this.selectionConfig!!.isGif = isGif
        return this
    }

    fun previewImage(enablePreview: Boolean): PictureSelectionModelReplace {
        this.selectionConfig!!.enablePreview = enablePreview
        return this
    }

    fun previewVideo(enPreviewVideo: Boolean): PictureSelectionModelReplace {
        this.selectionConfig!!.enPreviewVideo = enPreviewVideo
        return this
    }

    fun openClickSound(openClickSound: Boolean): PictureSelectionModelReplace {
        this.selectionConfig!!.openClickSound = openClickSound
        return this
    }

    fun selectionMedia(selectionMedia: List<LocalMedia>?): PictureSelectionModelReplace {
        var selectionMedia = selectionMedia
        if (selectionMedia == null) {
            selectionMedia = ArrayList()
        }

        this.selectionConfig!!.selectionMedias = selectionMedia
        return this
    }

    fun outlet(): PictureSelectorReplace? {
        return this.selector
    }

    fun forResult(requestCode: Int) {
        if (!DoubleUtils.isFastDoubleClick()) {
            val activity = this.selector!!.getActivity() ?: return

            val intent = Intent(activity, PictureSelectorActivity::class.java)
            val fragment = this.selector!!.getFragment()
            if (fragment != null) {
                fragment.startActivityForResult(intent, requestCode)
            } else {
                activity.startActivityForResult(intent, requestCode)
            }

            activity.overridePendingTransition(R.anim.a5, 0)
        }

    }
}
