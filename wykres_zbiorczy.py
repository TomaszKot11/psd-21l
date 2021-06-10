import csv 
import pandas as pd
import random
import matplotlib.pyplot as plt

df = pd.read_csv('dane_pośrednie.csv')


legend_dictionary = ['Średnia', 'Średnia z 10%', 'Mediana', 'Kwantyl', 'Odchylenie przeciętne', 'Średnia różnica Giniego']

for asset_id in range(1, 7):
  filtered_objs = df[df['assetId'] == asset_id]
  plt.title('Aktywo {}'.format(asset_id))
  for alert_id in range(1, 7): 
    x = filtered_objs[filtered_objs['alertId'] == alert_id]['windowId']
    y = filtered_objs[filtered_objs['alertId'] == alert_id]['percentage']
    r = random.random()
    b = random.random()
    g = random.random()
    color = [r, g, b]
    plt.scatter(x, y, c=color, label=legend_dictionary[alert_id-1])
    plt.xlabel('Indeks okna')
    plt.ylabel('Procentowe przekroczenie')
    plt.legend()
  plt.savefig('aktywo_{}_zbiorcze_wykres.png'.format(asset_id))
  plt.show()
