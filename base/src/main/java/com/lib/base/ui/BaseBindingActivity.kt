package com.lib.base.ui

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewbinding.ViewBinding

/**
 * 支持View Binding Activity
 */
abstract class BaseBindingActivity<VB : ViewBinding>(
    val block: (LayoutInflater) -> VB
) : BaseActivity() {
    private var _binding: VB? = null
    protected val binding: VB
        get() = requireNotNull(_binding) { "The property of binding has been destroyed." }

    override fun onCreate(savedInstanceState: Bundle?) {
        // 1. 先关闭系统 FitsSystemWindows，让我们自己处理 Insets
        WindowCompat.setDecorFitsSystemWindows(window, false)
        _binding = block(layoutInflater)
        setContentView(binding.root)
        super.onCreate(savedInstanceState)

        // 2. 处理根布局的安全区
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val sys = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(v.paddingLeft, 0, v.paddingRight, sys.bottom)
            WindowInsetsCompat.CONSUMED
        }

        // 3. 设置纯黑背景 —— 要在 setDecorFitsSystemWindows 之后、setContentView 之后调用
        window.apply {
            // 取消任何半透明标志
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
            // 确保不再设置成透明
            decorView.systemUiVisibility =
                decorView.systemUiVisibility and
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE.inv() and
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN.inv() and
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION.inv()

            // 真正改成纯黑
            statusBarColor = Color.BLACK
            navigationBarColor = Color.BLACK

            // Android 15+: 关闭对比度增强，否则纯黑可能被自动调亮
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                isStatusBarContrastEnforced = false
                isNavigationBarContrastEnforced = true
            }
        }

        // 4. 强制使用白色图标（关闭“light”模式）
        WindowCompat.getInsetsController(window, binding.root)?.apply {
            isAppearanceLightStatusBars = false     // false → 白色状态栏图标
            isAppearanceLightNavigationBars = true // false → 白色导航栏图标
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}