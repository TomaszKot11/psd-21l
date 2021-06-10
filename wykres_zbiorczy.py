import csv 
import pandas as pd
import matplotlib.pyplot as plt

df = pd.read_csv('wallet-1.csv')
some_value = df.groupby(['assetId', 'alertId']).agg(['count'])
assets_label = ['Aktywa 1', 'Aktywa 2', 'Aktywa 3', 'Aktywa 4', 'Aktywa 5', 'Aktywa 6']
legend_dictionary = ['Średnia', 'Średnia z 10%', 'Mediana', 'Kwantyl', 'Odchylenie przeciętnym', 'Średnia różnica Giniego']
fig = plt.figure(figsize=(12,4))

for i in range(1, 7): 
  current_column = some_value.loc[i]['windowId']
  x_y_matrix = list(map(lambda x: list(x), list(current_column.to_records(index=True))))
  print(x_y_matrix)
  x_ys = list(zip(*x_y_matrix)) 
  for j in current_column.to_records(index=True):
    plt.text(j[0], j[1] + 20000, " "+str(j[1]), color='black', va='center', fontweight='bold')
  plt.bar(*x_ys, color='#B5651D')
  labels = []
  for j in list(x_ys[0]): 
    labels.append(legend_dictionary[j-1])
  plt.xticks(x_ys[0], labels, fontsize='xx-small')
  plt.xlabel('Skrótowa nazwa statystyki')
  plt.ylabel('Sumaryczna liczba alertów (przekroczeń)')
  plt.title('Zestawienie przekroczeń dla alertu {}'.format(assets_label[i-1]))
  plt.savefig('aktywa_{}_wykres.png'.format(i))
  plt.show()
