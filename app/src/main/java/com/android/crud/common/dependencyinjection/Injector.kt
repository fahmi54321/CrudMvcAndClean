package com.android.crud.common.dependencyinjection

import com.android.crud.common.dependencyinjection.presentation.PresentationComponent
import com.android.crud.common.dependencyinjection.presentation.PresentationModule
import com.android.crud.view.common.dialog.DialogsNavigator
import com.android.crud.view.common.navigator.ScreenNavigator
import com.android.crud.view.common.viewmvc.ViewMvcFactory
import com.android.crud.view.karyawandetails.DetailsUseCase
import com.android.crud.view.karyawanform.FormKaryawanUserCase
import com.android.crud.view.karyawanviews.MainUseCase
import io.reactivex.rxjava3.disposables.CompositeDisposable
import java.lang.reflect.Field

class Injector(
    private val component: PresentationComponent
) {
    fun inject(client: Any) {
        for (field in getAllFields(client)) {
            if (isAnnotatedForInjection(field)) {
                injectField(client, field)
            }
        }
    }

    private fun getAllFields(client: Any): Array<out Field> {
        val clientClass = client::class.java
        return clientClass.declaredFields
    }

    private fun isAnnotatedForInjection(field: Field): Boolean {
        val fieldAnnotations = field.annotations
        for (annoation in fieldAnnotations) {
            if (annoation is Service) {
                return true
            }
        }
        return false
    }

    private fun injectField(client: Any, field: Field) {
        val isAccessibleInitially = field.isAccessible
        field.isAccessible = true
        field.set(client, getServiceForClass(field.type))
        field.isAccessible = isAccessibleInitially
    }

    private fun getServiceForClass(type: Class<*>): Any {
        when (type) {
            ScreenNavigator::class.java -> {
                return component.screenNavigator()
            }
            DialogsNavigator::class.java -> {
                return component.dialogsNavigator()
            }
            MainUseCase::class.java -> {
                return component.mainUseCase()
            }
            DetailsUseCase::class.java -> {
                return component.detailsUseCase()
            }
            FormKaryawanUserCase::class.java -> {
                return component.formKaryawanUserCase()
            }
            ViewMvcFactory::class.java -> {
                return component.viewMvcFactory()
            }
            CompositeDisposable::class.java -> {
                return component.compositeDisposable()
            }
            else -> {
                throw Exception("unsupported service type: $type")
            }
        }
    }
}