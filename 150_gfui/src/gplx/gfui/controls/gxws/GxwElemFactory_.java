/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.gfui.controls.gxws; import gplx.*; import gplx.gfui.*; import gplx.gfui.controls.*;
public class GxwElemFactory_ {
	public static GxwElemFactory_base Instance = new GxwElemFactory_cls_mock();
	public static void winForms_() {Instance = new GxwElemFactory_cls_lang();}
		public static void swt_(org.eclipse.swt.widgets.Display display) {Instance = new GxwElemFactory_swt(display);}
	}
