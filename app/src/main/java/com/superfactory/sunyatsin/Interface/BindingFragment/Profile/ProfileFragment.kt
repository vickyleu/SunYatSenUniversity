package com.superfactory.sunyatsin.Interface.BindingFragment.Profile

import com.superfactory.library.Bridge.Adapt.startActivity
import com.superfactory.library.Bridge.Adapt.startActivityForResult
import com.superfactory.library.Context.BaseToolbarFragment
import com.superfactory.sunyatsin.Interface.BindingActivity.MessageActivity.MessageActivity
import com.superfactory.sunyatsin.Interface.BindingActivity.QuestionnaireActivity.QuestionnaireActivity
import com.superfactory.sunyatsin.Interface.BindingActivity.SettingsActivity.SettingsActivity
import me.iwf.photopicker.PhotoPicker
import me.iwf.photopicker.utils.PermissionsUtils
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
                    val builder = PhotoPicker.builder()
                            .setPhotoCount(1)
                            .setShowCamera(true)
                            .setShowGif(true)
                            .setPreviewEnabled(false)
                    if (PermissionsUtils.checkReadStoragePermission(activity)) {
                        startActivityForResult(PhotoPicker.REQUEST_CODE, {
                            if (it != null) {
                                val photos = it.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS)
                                if (photos != null && photos.size > 0) {
                                    val photo = photos[0]
//                                    val sourceUri = Uri.parse(photo)
//                                    val destinationUri = Uri.fromFile(File(activity!!.cacheDir, "SampleCropImage.jpeg"))
//                                    val of = UCrop.of(sourceUri, destinationUri)
//                                    of.withAspectRatio(16f, 9f).withMaxResultSize(300, 300)
//                                    startActivityForResult(REQUEST_CROP, {
//                                        val croppedFileUri = UCrop.getOutput(it!!)
//                                        //获取默认的下载目录
//                                        val downloadsDirectoryPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath
//                                        val filename = String.format("%d_%s", Calendar.getInstance().timeInMillis, croppedFileUri?.lastPathSegment)
//                                        val saveFile = File(downloadsDirectoryPath, filename)
//                                        //保存下载的图片
//                                        var inStream: FileInputStream? = null
//                                        var outStream: FileOutputStream? = null
//                                        var inChannel: FileChannel? = null
//                                        var outChannel: FileChannel? = null
//                                        try {
//                                            inStream = FileInputStream(File(croppedFileUri?.path))
//                                            outStream = FileOutputStream(saveFile)
//                                            inChannel = inStream.channel
//                                            outChannel = outStream.channel
//                                            inChannel!!.transferTo(0, inChannel.size(), outChannel)
//                                            Toast.makeText(context, "裁切后的图片保存在：" + saveFile.absolutePath, Toast.LENGTH_SHORT).show()
//                                        } catch (e: Exception) {
//                                            e.printStackTrace()
//                                        } finally {
//                                            try {
//                                                outChannel!!.close()
//                                                outStream!!.close()
//                                                inChannel!!.close()
//                                                inStream!!.close()
//                                            } catch (e: Exception) {
//                                                e.printStackTrace()
//                                            }
//
//                                        }
//                                    }, of.getIntent(activity!!))
                                }
                            }
                        }, builder.getIntent(activity!!))
                    }
                }
                4/*"设置"*/ -> {
                    startActivity<SettingsActivity>()
                }
            }
        }
    }

}


