package com.superfactory.library.Graphics.HUD.Base

import com.superfactory.library.Bridge.Anko.BindingComponent

/**
 * Created by vicky on 2018/1/28.
 */
abstract class HUDComponent<V : LoadingHudModel, A : AnkoHUD<V, A>>(viewModel: V?) : BindingComponent<A, V>(viewModel) {

}