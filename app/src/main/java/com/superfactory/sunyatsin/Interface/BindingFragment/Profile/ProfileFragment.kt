package com.superfactory.sunyatsin.Interface.BindingFragment.Profile

import android.net.Uri
import com.luck.picture.lib.PictureSelectorActivity
import com.luck.picture.lib.R
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.tools.DoubleUtils
import com.luck.picture.lib.tools.PictureFileUtils.getPath
import com.superfactory.library.Bridge.Adapt.startActivity
import com.superfactory.library.Bridge.Adapt.startActivityForResult
import com.superfactory.library.Context.BaseToolbarFragment
import com.superfactory.library.Utils.FileUtil
import com.superfactory.sunyatsin.Interface.BindingActivity.MessageActivity.MessageActivity
import com.superfactory.sunyatsin.Interface.BindingActivity.QuestionnaireActivity.QuestionnaireActivity
import com.superfactory.sunyatsin.Interface.BindingActivity.SettingsActivity.SettingsActivity
import com.superfactory.sunyatsin.Model.PictureSelectorReplace
import org.jetbrains.anko.support.v4.startActivity


/**
 * Created by vicky on 2018.01.19.
 *
 * @Author vicky
 * @Date 2018年01月19日  13:40:24
 * @ClassName 这里输入你的类名(或用途)
 */
class ProfileFragment : BaseToolbarFragment<ProfileFragmentViewModel, ProfileFragment>() {

    override fun newViewModel(): ProfileFragmentViewModel {
        val model = ProfileFragmentViewModel(extra)
        return model
    }

    override fun newComponent(v: ProfileFragmentViewModel) = ProfileFragmentComponent(v).apply {
        viewModelSafe.ownerNotifier = { i, any ->
            when (i) {
                102 -> {
                    startActivity<MessageActivity>(Pair("data", any))
                }
                else -> {
                    startActivityForResult<QuestionnaireActivity>(1001, {

                    }, Pair("data", any))
                }
            }

        }
    }

    override fun destroyModel(viewModel: ProfileFragmentViewModel?) {
        super.destroyModel(viewModel)
        if (viewModel != null) {
//            viewModel.ba
        }
    }

    override fun onLoadedModel(viewModel: ProfileFragmentViewModel) {
        viewModel.onItemClicked = { idx, model ->
            when (idx) {
                -1/*"头像"*/ -> {

                    // 进入相册 以下是例子：用不到的api可以不写
                    val selector = PictureSelectorReplace
                            .create(activity!!)
                            .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                            .maxSelectNum(1)// 最大图片选择数量 int
                            .minSelectNum(1)// 最小选择数量 int
                            .imageSpanCount(4)// 每行显示个数 int
                            .previewImage(true)// 是否可预览图片 true or false
                            .isCamera(true)// 是否显示拍照按钮 true or false
                            .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                            .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                            .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                            .setOutputCameraPath("/CustomPath")// 自定义拍照保存路径,可不填
                            .enableCrop(true)// 是否裁剪 true or false
                            .compress(true)// 是否压缩 true or false
                            .withAspectRatio(16, 9)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
//                            .hideBottomControls()// 是否显示uCrop工具栏，默认不显示 true or false
                            .compressSavePath(getPath(context, Uri.fromFile(FileUtil.createTmpFile(context!!))))//压缩图片保存地址
                            .freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
                            .circleDimmedLayer(true)// 是否圆形裁剪 true or false
                            .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                            .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                            .openClickSound(false)// 是否开启点击声音 true or false
                            .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                            .minimumCompressSize(500)// 小于100kb的图片不压缩
                            .synOrAsy(false)//同步true或异步false 压缩 默认同步
                            .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
                            .outlet()
                    if (!DoubleUtils.isFastDoubleClick()) {
                        startActivityForResult<PictureSelectorActivity>(PictureConfig.CHOOSE_REQUEST, {

                        })
                        selector!!.getActivity()?.overridePendingTransition(R.anim.a5, 0)
                    }
                }
                4/*"设置"*/ -> {
                    startActivity<SettingsActivity>()
                }
            }
        }
    }

}


