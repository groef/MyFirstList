package com.hubeleon.data.provider;

import java.util.Iterator;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.core.util.lang.PropertyResolver;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortState;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortStateLocator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;
import org.apache.wicket.extensions.markup.html.repeater.util.SingleSortState;
import org.apache.wicket.extensions.markup.html.repeater.util.SortParam;
import org.apache.wicket.markup.repeater.data.ListDataProvider;

import com.hubeleon.data.dao.CDRInfoDAO;
import com.hubeleon.model.CDRInfo;

public class CDRInfoProvider extends ListDataProvider<CDRInfo> implements ISortableDataProvider<CDRInfo,String> {

	private static final long serialVersionUID = 1L;

	private final SingleSortState<String> state = new SingleSortState<String>();

	public CDRInfoProvider()
	{
		super();
		System.out.println(" !!! ####### : init CDRInfoProvider ");		
	}

	@Override
	public List<CDRInfo> getData()
	{	
		System.out.println(" !!! ####### : getData()");
		List<CDRInfo> list = CDRInfoDAO.all();
		System.out.println(" !!! ####### : list size " + list.size());
		
		SortParam<String> param =  this.state.getSort();

		if (param != null)
		{
			Collections.sort(list, new CDRInfoComparator(param.getProperty(), param.isAscending()));
		}
		System.out.println(" !!! ####### : list that will be returned size " + list.size());
		
		return list;
	}

	public ISortState<String> getSortState()
	{
		System.out.println(" !!! ####### : getSortState()");
		return this.state;
	}

	static class CDRInfoComparator implements Comparator<CDRInfo>, Serializable
	{
		private static final long serialVersionUID = 1L;

		private final String property;
		private final boolean ascending;

		public CDRInfoComparator(String property, boolean ascending)
		{
			this.property = property;
			this.ascending = ascending;
		}

		@Override
		public int compare(CDRInfo p1, CDRInfo p2)
		{
			Object o1 = PropertyResolver.getValue(this.property, p1);
			Object o2 = PropertyResolver.getValue(this.property, p2);

			if (o1 != null && o2 != null)
			{
				Comparable<Object> c1 = toComparable(o1);
				Comparable<Object> c2 = toComparable(o2);

				return c1.compareTo(c2) * (this.ascending ? 1 : -1);
			}

			return 0;
		}

		@SuppressWarnings("unchecked")
		private static Comparable<Object> toComparable(Object o)
		{
			if (o instanceof Comparable<?>)
			{
				return (Comparable<Object>) o;
			}

			throw new WicketRuntimeException("Object should be a Comparable");
		}
	}

}
