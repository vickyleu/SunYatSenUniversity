package com.superfactory.sunyatsin.Interface.BindingFragment.Profile

import com.superfactory.library.Context.BaseToolbarFragment
import com.superfactory.library.Debuger
import com.superfactory.sunyatsin.Interface.BindingActivity.QuestionnaireActivity.QuestionnaireActivity
import com.superfactory.sunyatsin.Interface.BindingActivity.SettingsActivity.SettingsActivity
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.startActivityForResult


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
        viewModelSafe.ownerNotifier = { _, any ->
            if (any == null) {
                Debuger.printMsg(this, "数据不能为空啊")
            } else {
                startActivityForResult<QuestionnaireActivity>(1001, Pair("data", any))
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
//                    PictureSelector.create(activity)
//                            .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
//                            .maxSelectNum(1)// 最大图片选择数量 int
//                            .minSelectNum(1)// 最小选择数量 int
//                            .imageSpanCount(4)// 每行显示个数 int
//                            .previewImage(true)// 是否可预览图片 true or false
//                            .isCamera(true)// 是否显示拍照按钮 true or false
//                            .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
//                            .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
//                            .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
//                            .setOutputCameraPath("/CustomPath")// 自定义拍照保存路径,可不填
//                            .enableCrop(true)// 是否裁剪 true or false
//                            .compress(true)// 是否压缩 true or false
//                            .glideOverride(500,500)// int glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
//                            .withAspectRatio(16,9)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
//                            .hideBottomControls(false)// 是否显示uCrop工具栏，默认不显示 true or false
//                            .isGif(true)// 是否显示gif图片 true or false
////                            .compressSavePath(getPath(context, Uri.fromFile()))//压缩图片保存地址
//                            .freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
//                            .circleDimmedLayer(true)// 是否圆形裁剪 true or false
//                            .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
//                            .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
//                            .openClickSound(false)// 是否开启点击声音 true or false
////                            .selectionMedia()// 是否传入已选图片 List<LocalMedia> list
//                            .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
//                            .cropCompressQuality(90)// 裁剪压缩质量 默认90 int
//                            .minimumCompressSize(500)// 小于100kb的图片不压缩
//                            .synOrAsy(false)//同步true或异步false 压缩 默认同步
////                            .cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效 int
////                            .rotateEnabled() // 裁剪是否可旋转图片 true or false
//                            .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
////                            .videoQuality()// 视频录制质量 0 or 1 int
////                            .videoMaxSecond(15)// 显示多少秒以内的视频or音频也可适用 int
////                            .videoMinSecond(10)// 显示多少秒以内的视频or音频也可适用 int
////                            .recordVideoSecond()//视频秒数录制 默认60s int
//                            .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
                }
//                0/*"警号"*/ -> {
//                    startActivity<MainActivity>()
//                }
//                1/*"部门"*/ -> {
//                    startActivity<MainActivity>()
//                }
//                2/*"岗位"*/ -> {
//                    startActivity<MainActivity>()
//                }
//                3/*"职务"*/ -> {
//                    startActivity<MainActivity>()
//                }
                3/*"问卷"*/ -> {

//
                }
                4/*"设置"*/ -> {
                    startActivity<SettingsActivity>()
                }
            }
        }
    }

}


