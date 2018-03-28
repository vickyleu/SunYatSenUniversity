package com.superfactory.sunyatsen.Bean

import com.superfactory.sunyatsen.Bean.BaseBean.MultipartBean

/**
 * Created by vicky on 2018.02.05.
 *
 * @Author vicky
 * @Date 2018年02月05日  14:07:58
 * @ClassName 这里输入你的类名(或用途)
 */
data class QuestionnaireCommitBean(val param: List<QuestionnaireCommitItem>,val parentId:String) : MultipartBean(){

}

data class QuestionnaireCommitItem(val optionId:String,val remark:String,val scope:String,val questionId:String)