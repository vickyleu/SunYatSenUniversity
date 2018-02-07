package com.superfactory.sunyatsin.Interface.BindingActivity.AccountActivity

import android.net.Uri
import com.luck.picture.lib.PictureSelectorActivity
import com.luck.picture.lib.R
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.tools.DoubleUtils
import com.luck.picture.lib.tools.PictureFileUtils
import com.superfactory.library.Bridge.Adapt.startActivityForResult
import com.superfactory.library.Context.BaseToolBarActivity
import com.superfactory.library.Utils.FileUtil
import com.superfactory.sunyatsin.Interface.BindingActivity.CompellationActivity.CompellationActivity
import com.superfactory.sunyatsin.Interface.BindingActivity.GenderActivity.GenderActivity
import com.superfactory.sunyatsin.Model.PictureSelectorReplace

/**
 * Created by vicky on 2018.01.31.
 *
 * @Author vicky
 * @Date 2018年01月31日  18:55:06
 * @ClassName 这里输入你的类名(或用途)
 */
class AccountActivity : BaseToolBarActivity<AccountActivityViewModel, AccountActivity>() {
    override fun newViewModel() = AccountActivityViewModel(intent)

    override fun newComponent(v: AccountActivityViewModel) = AccountActivityComponent(v)

    override fun onLoadedModel(viewModel: AccountActivityViewModel) {
        viewModel.onItemClicked = { idx, model ->
            when (idx) {
                0/*头像*/ -> {
                    // 进入相册 以下是例子：用不到的api可以不写
                    val selector = PictureSelectorReplace
                            .create(this)
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
                            .compressSavePath(PictureFileUtils.getPath(this, Uri.fromFile(FileUtil.createTmpFile(this))))//压缩图片保存地址
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
                1/*姓名*/ -> {
                    startActivityForResult<CompellationActivity>(101, { intent ->

                    })
                }
                2/*性别*/ -> {
                    startActivityForResult<GenderActivity>(101, { intent ->
//                        val gender=intent?.extras?.getInt("gender")
                    })
                }
            }
        }
    }

}