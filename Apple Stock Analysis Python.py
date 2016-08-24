
# coding: utf-8

# In[1]:

import pandas


# In[2]:

apple = DataReader('AAPL', 'yahoo', start = '01/01/2015', end = '24/12/2015')


# In[3]:

apple = DataReader('AAPL', 'yahoo', start = '01/01/2012', end = '25/10/2012')


# In[4]:

pd.read_csv('Apple_NYSE_Analysis.csv', parse_dates=True, index_col=0, sep=',')


# In[5]:

df = pd.read_csv('my_data.csv', parse_dates=True, index_col=0, sep=',')


# In[6]:

from pandas.io import data as pddata


# In[7]:

from pandas.io import data as pddata


# In[8]:

df = pddata.DataReader('AAPL', data_source='yahoo', start='2012-01-01')


# In[9]:

df.resample('1M').sort('Close')


# In[ ]:



