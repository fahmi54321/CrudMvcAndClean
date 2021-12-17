package com.android.crud.common.dependencyinjection.activity

import android.app.Application
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.android.crud.common.dependencyinjection.app.AppComponent
import com.android.crud.common.dependencyinjection.presentation.PresentationComponent
import com.android.crud.common.dependencyinjection.presentation.PresentationModule
import com.android.crud.network.RestApi
import com.android.crud.view.common.navigator.ScreenNavigator
import dagger.Component
import dagger.Provides
import dagger.Subcomponent
import io.reactivex.rxjava3.disposables.CompositeDisposable

@ActivityScope
@Subcomponent(modules = [ActivityModule::class])
interface ActivityComponent {

    fun newPresentationComponent(presentationModule: PresentationModule):PresentationComponent

}