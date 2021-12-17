package com.android.crud.common.dependencyinjection

import com.android.crud.view.common.dialog.DialogsNavigator
import com.android.crud.view.common.navigator.ScreenNavigator
import com.android.crud.view.common.viewmvc.ViewMvcFactory
import com.android.crud.view.karyawandetails.DetailsActivity
import com.android.crud.view.karyawandetails.DetailsUseCase
import com.android.crud.view.karyawanform.FormKaryawanActivity
import com.android.crud.view.karyawanform.FormKaryawanUserCase
import com.android.crud.view.karyawanviews.MainActivity
import com.android.crud.view.karyawanviews.MainUseCase
import io.reactivex.rxjava3.disposables.CompositeDisposable
import java.lang.reflect.Field

class Injector(
    private val compositionRoot: PresentationCompositionRoot
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
                return compositionRoot.screenNavigator
            }
            DialogsNavigator::class.java -> {
                return compositionRoot.dialogsNavigator
            }
            MainUseCase::class.java -> {
                return compositionRoot.mainUseCase
            }
            DetailsUseCase::class.java -> {
                return compositionRoot.detailsUseCase
            }
            FormKaryawanUserCase::class.java -> {
                return compositionRoot.formKaryawanUserCase
            }
            ViewMvcFactory::class.java -> {
                return compositionRoot.viewMvcFactory
            }
            CompositeDisposable::class.java -> {
                return compositionRoot.compositeDisposable
            }
            else -> {
                throw Exception("unsupported service type: $type")
            }
        }
    }
}