package com.common.cook.module.common.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.common.cook.module.common.contract.ShareContract;
import com.common.cook.module.common.contract.WebContract;
import com.common.cook.module.common.model.ShareModel;
import com.common.cook.module.common.model.WebModel;

import dagger.Module;
import dagger.Provides;


@Module
public class WebModule {
    private WebContract.View view;

    /**
     * 构建WebModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public WebModule(WebContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    WebContract.View provideWebView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    WebContract.Model provideWebModel(WebModel model) {
        return model;
    }

    @ActivityScope
    @Provides
    ShareContract.Model provideShareModule(IRepositoryManager repositoryManager){
        return new ShareModel(repositoryManager);
    }
}